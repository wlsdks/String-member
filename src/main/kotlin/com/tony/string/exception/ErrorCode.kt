package com.tony.string.exception

enum class ErrorCode(
    val code: String,
    val message: String
) {
    // jwt 관련 에러
    INVALID_SIGNATURE("INVALID_SIGNATURE", "Invalid JWT signature."),
    MALFORMED_TOKEN("MALFORMED_TOKEN", "Malformed JWT token."),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "Expired JWT token."),

    // 스프링 시큐리티 예외
    ACCESS_DENIED("ACCESS_DENIED", "Access is denied."),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", "Authentication failed."),

    // 기본 예외, 런타임 예외
    INTERNAL_ERROR("INTERNAL_ERROR", "Internal server error."),
    RUNTIME_EXCEPTION("RUNTIME_EXCEPTION", "Unexpected runtime error.")

    // 커스텀 예외 (생길때마다 추가)

}