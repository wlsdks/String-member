package com.tony.string.config.security.filter.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.tony.string.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.*
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.io.IOException

/**
 * 5. Spring Security 기반의 사용자의 정보가 맞지 않을 경우 수행이 되며 결과값을 리턴해주는 Handler
 * customLoginFailureHandler: 이 메서드는 인증 실패 핸들러를 생성한다. 인증 실패 핸들러는 인증 실패시 수행할 작업을 정의한다.
 */
@Component
class CustomAuthFailureHandler(
    private val objectMapper: ObjectMapper
) : AuthenticationFailureHandler {

    val log = logger()

    /**
     * 인증 실패 시 처리하는 메서드
     */
    @Throws(IOException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val failMessage = getFailureMessage(exception)
        val resultMap = mapOf(
            "memberInfo" to null,
            "resultCode" to "FAIL",
            "failMessage" to failMessage
        )

        response.characterEncoding = "UTF-8"
        response.contentType = "application/json"
        response.writer.use { it.print(objectMapper.writeValueAsString(resultMap)) }
    }

    /**
     * 인증 중 발생한 예외의 타입에 따라 실패 메시지를 반환합니다.
     */
    private fun getFailureMessage(exception: AuthenticationException): String = when (exception) {
        is BadCredentialsException -> "로그인 정보가 일치하지 않습니다."
        is LockedException -> "계정이 잠겨 있습니다."
        is DisabledException -> "계정이 비활성화되었습니다."
        is AccountExpiredException -> "계정이 만료되었습니다."
        is CredentialsExpiredException -> "인증 정보가 만료되었습니다."
        else -> "알 수 없는 오류가 발생하였습니다."
    }
}