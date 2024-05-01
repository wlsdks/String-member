package com.tony.string.exception

enum class ErrorCode(
    val code: String,
    val message: String,
) {
    // jwt 관련 에러
    INVALID_SIGNATURE("INVALID_SIGNATURE", "Invalid JWT signature."),
    MALFORMED_TOKEN("MALFORMED_TOKEN", "Malformed JWT token."),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "Expired JWT token."),

    // 스프링 시큐리티 예외
    ACCESS_DENIED("ACCESS_DENIED", "접근 권한이 없습니다. (security roles)"),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", "인증에 실패하였습니다. (security authentication)"),
    FILTER_USERNAME_PASSWORD_AUTHENTICATION_TOKEN_EXCEPTION(
        "FILTER_USERNAME_PASSWORD_AUTHENTICATION_TOKEN_EXCEPTION",
        "UsernamePasswordAuthenticationToken Exception occurred."
    ),
    AUTHENTICATION_NOT_FOUND("AUTHENTICATION_NOT_FOUND", "인증 정보를 찾을 수 없습니다."),
    UNAUTHENTICATED("UNAUTHENTICATED", "사용자가 인증되지 않았습니다."),
    PRINCIPAL_NOT_CORRECT_TYPE("PRINCIPAL_NOT_CORRECT_TYPE", "시큐리티의 Principle 타입이 JwtMemberInfoDTO 타입이 아닙니다."),

    // 기본 예외, 런타임 예외
    INTERNAL_ERROR("INTERNAL_ERROR", "Internal server error occurred."),
    RUNTIME_EXCEPTION("RUNTIME_EXCEPTION", "Unexpected runtime error."),
    IO_EXCEPTION("IO_EXCEPTION", "I/O error occurred."),

    // 커스텀 예외 (생길때마다 추가)
}
