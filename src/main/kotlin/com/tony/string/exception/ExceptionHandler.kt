package com.tony.string.exception

import com.tony.string.controller.response.ApiResponse
import com.tony.string.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    val log = logger()

    // 다른 표준 예외들을 위한 핸들러...
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<Any> {
        log.error("Exception: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(ErrorCode.INTERNAL_ERROR.code, ErrorCode.INTERNAL_ERROR.message))
    }

    // 런타임 예외
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<Any> {
        log.error("Exception: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(ErrorCode.RUNTIME_EXCEPTION.code, ErrorCode.RUNTIME_EXCEPTION.message))
    }

    // 커스텀 예외
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): ResponseEntity<Any> {
        log.error("Exception: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(ex.errorCode.code, ex.errorCode.message))
    }

//    // jwt 서명 예외
//    @ExceptionHandler(SignatureException::class)
//    fun handleSignatureException(ex: SignatureException?): ResponseEntity<Any> {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//            .body(ApiResponse.error(ErrorCode.INVALID_SIGNATURE.code, ErrorCode.INVALID_SIGNATURE.message))
//    }
//
//    // jwt 변조 예외
//    @ExceptionHandler(MalformedJwtException::class)
//    fun handleMalformedJwtException(ex: MalformedJwtException?): ResponseEntity<Any> {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//            .body(ApiResponse.error(ErrorCode.MALFORMED_TOKEN.code, ErrorCode.MALFORMED_TOKEN.message))
//    }
//
//    // jwt 만료 예외
//    @ExceptionHandler(ExpiredJwtException::class)
//    fun handleExpiredJwtException(ex: ExpiredJwtException?): ResponseEntity<Any> {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//            .body(ApiResponse.error(ErrorCode.EXPIRED_TOKEN.code, ErrorCode.EXPIRED_TOKEN.message))
//    }

    // 시큐리티 권한 예외
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException?): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.error(ErrorCode.ACCESS_DENIED.code, ErrorCode.ACCESS_DENIED.message))
    }

    // 시큐리티 인증 예외
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException?): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.error(ErrorCode.AUTHENTICATION_FAILED.code, ErrorCode.AUTHENTICATION_FAILED.message))
    }

}
