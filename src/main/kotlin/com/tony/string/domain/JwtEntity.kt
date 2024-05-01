package com.tony.string.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "jwt")
data class JwtEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jwt_id", nullable = false)
    val id: Long?,
    @Column(name = "member_id", nullable = false)
    val memberId: Long,
    @Column(name = "refresh_token", nullable = false, length = 500)
    val refreshToken: String,
    @Column(name = "expired_datetime", nullable = false)
    val expiredDateTime: LocalDateTime,
) {
    companion object {
        fun of(
            memberId: Long,
            refreshToken: String,
            expiredDateTime: LocalDateTime,
        ) = JwtEntity(
            id = null,
            memberId = memberId,
            refreshToken = refreshToken,
            expiredDateTime = expiredDateTime
        )

        fun isRefreshTokenValid(
            now: LocalDateTime,
            expiredDateTime: LocalDateTime?,
        ): Boolean {
            return now.isBefore(expiredDateTime)
        }
    }
}
