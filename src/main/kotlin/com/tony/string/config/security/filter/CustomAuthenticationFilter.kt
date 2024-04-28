package com.tony.string.config.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.tony.string.config.security.dto.JwtMemberInfoDTO
import com.tony.string.config.security.filter.handler.CustomAuthFailureHandler
import com.tony.string.config.security.filter.handler.CustomAuthSuccessHandler
import com.tony.string.exception.CustomException
import com.tony.string.exception.ErrorCode
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

/**
 * 1. 커스텀을 수행한 '인증' 필터로 접근 URL, 데이터 전달방식(form) 등 인증 과정 및 인증 후 처리에 대한 설정을 구성하는 메서드다.
 * 이 메서드는 사용자 정의 인증 필터를 생성한다. 이 필터는 로그인 요청을 처리하고, 인증 성공/실패 핸들러를 설정한다.
 *
 * @see AuthenticationManagerConfig
 */
@Component
class CustomAuthenticationFilter(
    private val authenticationManager: AuthenticationManager,
    private val customAuthFailureHandler: CustomAuthFailureHandler,
    private val customAuthSuccessHandler: CustomAuthSuccessHandler
) : UsernamePasswordAuthenticationFilter() {

    private val objectMapper: ObjectMapper = ObjectMapper()

    @PostConstruct
    fun initialize() {
        super.setAuthenticationManager(authenticationManager)  // AuthenticationManager 설정
        objectMapper.registerModule(JavaTimeModule())          // JavaTimeModule 등록
        setFilterProcessesUrl("/auth/login")                   // login URL 설정
        setAuthenticationSuccessHandler(customAuthSuccessHandler)
        setAuthenticationFailureHandler(customAuthFailureHandler)
    }

    /**
     * 이 메서드는 사용자가 로그인을 시도할 때 호출된다.
     * HTTP 요청에서 사용자 이름과 비밀번호를 추출하여 UsernamePasswordAuthenticationToken 객체를 생성하고, 이를 AuthenticationManager에 전달하여 인증을 시도한다.
     * 인증이 성공하면 인증된 사용자의 정보와 권한을 담은 Authentication 객체를 반환하고, 인증이 실패하면 AuthenticationException을 던진다.
     */
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse?
    ): Authentication {
        try {
            val jwtMemberInfoDTO = objectMapper.readValue(
                request.inputStream,
                JwtMemberInfoDTO::class.java
            )
            val authRequest = UsernamePasswordAuthenticationToken(
                jwtMemberInfoDTO.email,
                jwtMemberInfoDTO.password
            )
            setDetails(request, authRequest)
            return authenticationManager.authenticate(authRequest)
        } catch (e: Exception) {
            throw CustomException(ErrorCode.FILTER_USERNAME_PASSWORD_AUTHENTICATION_TOKEN_EXCEPTION, e.message!!)
        }
    }
}