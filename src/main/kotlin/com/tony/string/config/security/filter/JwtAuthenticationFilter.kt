package com.tony.string.config.security.filter

import com.tony.string.config.security.dto.JwtMemberInfoDTO
import com.tony.string.config.security.jwt.JwtUtils
import com.tony.string.config.security.jwt.JwtValidator
import com.tony.string.logger
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

/**
 * JwtAuthenticationFilter 클래스는 JWT 토큰을 사용해 인증을 수행하는 필터입니다.
 * 이 필터의 주요 역할은 HTTP 요청의 Authorization 헤더에서 토큰을 추출하고, 이 토큰의 유효성을 검사한 후,
 * 유효한 토큰이라면 토큰에서 사용자의 정보를 추출하여 Spring Security의 SecurityContext에 인증 정보를 등록하는 것입니다.
 */
@Component
class JwtAuthenticationFilter(
    private val jwtValidator: JwtValidator,
    private val jwtUtils: JwtUtils,
) : OncePerRequestFilter() {

    val log = logger()
    private val AUTHORIZATION_HEADER = "Authorization"
    private val ACCESS_TOKEN_TYPE = "access"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request.getHeader(AUTHORIZATION_HEADER))

        // token 검증 및 SecurityContext에 인증 정보 등록
        if (validateToken(token)) {
            val authenticationToken = extractUserDetails(token)
            SecurityContextHolder.getContext().authentication = authenticationToken
        } else {
            log.debug("No valid JWT token found or token validation failed")
        }
        filterChain.doFilter(request, response)
    }

    // 요청 헤더에서 Authorization 헤더를 읽어 JWT 토큰을 추출합니다.
    private fun extractToken(authHeader: String?): String? {
        return authHeader?.let {
            jwtUtils.extractAccessToken(it)
        }
    }

    // 토큰의 유효성을 검증하는 메서드
    private fun validateToken(token: String?): Boolean =
        jwtValidator.isValidToken(token, ACCESS_TOKEN_TYPE).also {
            if (!it) log.debug("Invalid JWT token: {}", token)
        }

    /**
     * 토큰에서 사용자의 정보를 추출하고, 이를 사용하여 UsernamePasswordAuthenticationToken을 생성한 다음,
     * 이 토큰을 SecurityContextHolder에 설정하여 사용자가 인증된 것으로 처리합니다.
     */
    private fun extractUserDetails(token: String?): UsernamePasswordAuthenticationToken {
        // JWT 토큰에서 사용자 정보를 추출
        val memberId = jwtUtils.getMemberIdFromToken(token)
        val email = jwtUtils.getEmailFromToken(token)
        val role = jwtUtils.getRoleFromToken(token)

        // 정보를 추출해서 UsernamePasswordAuthenticationToken 객체 생성
        val authorities = listOf(SimpleGrantedAuthority(role))
        val jwtMemberInfoDto = JwtMemberInfoDTO.fromDto(memberId, email)
        return UsernamePasswordAuthenticationToken(jwtMemberInfoDto, null, authorities)
    }
}