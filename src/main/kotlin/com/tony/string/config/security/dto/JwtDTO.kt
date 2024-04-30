package com.tony.string.config.security.dto

import java.time.LocalDateTime

/**
 * Jwt 도메인 객체
 */
data class JwtDTO(
    val id: Long,
    val memberId: Long,
    val refreshToken: String,
    val expiredDateTime: LocalDateTime
) {
    /**
     * Refresh token의 유효성을 확인한다.
     *
     * @param now 현재 시간
     * @param expiredDateTime 토큰의 만료 시간
     * @return 현재 시간이 expiredDateTime보다 이전이면 true, 그렇지 않으면 false
     */
    fun isRefreshTokenValid(now: LocalDateTime, expiredDateTime: LocalDateTime?): Boolean {
        return now.isBefore(expiredDateTime)
    }
}
