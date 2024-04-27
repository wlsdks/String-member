package com.tony.string.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * 토큰에 담긴 정보로 사용자를 필터링해줄 필터입니다.
 */
@Order(0)
@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = extractBearerToken(request)
            val user = getUserFromToken(token)
            authenticateUser(user, token, request)
        } catch (e: Exception) {
            request.setAttribute("exception", e) // 여기서 예외를 잡아서 request에 추가한다.
        }

        filterChain.doFilter(request, response)
    }

    /**
     * Authorization 헤더에서 "Bearer" 토큰을 추출합니다.
     * Kotlin의 substringAfter 메서드를 사용하여 "Bearer " 이후의 문자열을 반환하거나 빈 문자열을 반환합니다.
     */
    private fun extractBearerToken(request: HttpServletRequest): String? =
        request.getHeader(HttpHeaders.AUTHORIZATION)?.substringAfter("Bearer ", "")

    /**
     * 제공된 토큰에서 사용자 정보를 추출하거나 "anonymous" 사용자로 폴백합니다.
     * 토큰이 유효하면 JwtProvider를 사용하여 subject를 가져오고, 그렇지 않으면 "anonymous" 사용자를 반환합니다.
     */
    private fun getUserFromToken(token: String?): User {
        val userDetails = token
            ?.takeIf { it.isNotBlank() }                         // token이 비어있지 않은 경우에만 token 값을 유지하고, 비어있다면 null을 결과로 반환
            ?.let { jwtProvider.validateTokenAndGetSubject(it) } // token을 받아서 토큰을 검증하고, 해당 토큰의 'subject'를 반환
            ?.split(":")                              // "username:role" 형태의 문자열이 있다면 ["username", "role"] 형태의 리스트로 변환
            ?: listOf("anonymous", "anonymous")                  // 엘비스 연산자: 토큰이 유효하지 않거나 subject가 없는 경우 기본적으로 ["anonymous", "anonymous"]를 userDetails로 설정

        return User(userDetails[0], "", listOf(SimpleGrantedAuthority(userDetails[1])))
    }

    /**
     * 인증 객체를 생성하고 Spring Security의 SecurityContext에 설정합니다.
     * 이 메서드에서는 UsernamePasswordAuthenticationToken을 사용하여 사용자의 인증 정보를 등록합니다.
     */
    private fun authenticateUser(user: User, token: String?, request: HttpServletRequest) {
        UsernamePasswordAuthenticationToken(user, token, user.authorities).apply {
            details = WebAuthenticationDetails(request)
        }.also { SecurityContextHolder.getContext().authentication = it }
    }
}