package com.tony.string.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

/**
 * 이 엔트리포인트는 스프링 시큐리티에서 인증과 관련된 예외가 발생했을 때 이를 처리하는 로직을 담당한다.
 * 여기서 HandlerExceptionResolver의 빈을 주입받고 있는데, HandlerExceptionResolver의 빈이 두 종류가 있기 때문에 @Qualifier로 handlerExceptionResolver를 주입받을 것이라고 명시해줘야 한다.
 */
@Component
class JwtAuthenticationEntryPoint(
    @Qualifier("handlerExceptionResolver") private val resolver: HandlerExceptionResolver
) : AuthenticationEntryPoint {

    /**
     * commence()에서 스프링 시큐리티의 인증 관련 예외를 처리하게 된다.
     * ControllerAdvice에서 모든 예외를 처리하여 응답할 것이기 때문에 여기에 별다른 로직은 작성하지 않고 HandlerExceptionResolver에 예외 처리를 위임한다.
     */
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        // JwtAuthenticationFilter에서 request에 담아서 보내준 예외를 처리
        resolver.resolveException(request!!, response!!, null, request.getAttribute("exception") as Exception)
    }
}