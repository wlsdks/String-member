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
class CustomAuthFailureHandler(private val objectMapper: ObjectMapper) : AuthenticationFailureHandler {

    val log = logger()

    @Throws(IOException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val resultMap: MutableMap<String, Any?> = HashMap()
        val failMessage = getFailureMessage(exception)
        log.error(failMessage)

        resultMap["memberInfo"] = null
        resultMap["resultCode"] = 9999
        resultMap["failMessage"] = failMessage

        response.characterEncoding = "UTF-8"
        response.contentType = "application/json"

        response.writer.use { printWriter ->
            printWriter.print(objectMapper.writeValueAsString(resultMap))
            printWriter.flush()
        }
    }

    /**
     * 인증 중 발생한 예외의 타입에 따라 실패 메시지를 반환합니다.
     *
     * @param exception 인증 중 발생한 예외 정보
     * @return 실패 메시지
     */
    private fun getFailureMessage(exception: AuthenticationException): String {
        if (exception is BadCredentialsException) {
            return "로그인 정보가 일치하지 않습니다."
        } else if (exception is LockedException) {
            return "계정이 잠겨 있습니다."
        } else if (exception is DisabledException) {
            return "계정이 비활성화되었습니다."
        } else if (exception is AccountExpiredException) {
            return "계정이 만료되었습니다."
        } else if (exception is CredentialsExpiredException) {
            return "인증 정보가 만료되었습니다."
        }
        return "알 수 없는 오류가 발생하였습니다."
    }
}