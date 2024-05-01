package com.tony.string.domain.dto

/**
 * 로그아웃 DTO
 */
data class LogoutDTO(
    val memberId: Long,
    val accessToken: String,
) {
    companion object {
        fun of(
            memberId: Long,
            accessToken: String,
        ): LogoutDTO {
            return LogoutDTO(memberId, accessToken)
        }
    }
}
