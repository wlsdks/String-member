package com.tony.string.repository

import com.tony.string.domain.Jwt
import org.springframework.data.jpa.repository.JpaRepository

interface JwtRepository : JpaRepository<Jwt, Long> {
    fun deleteJwtByMemberId(memberId: Long)

    fun findJwtByMemberIdAndRefreshToken(
        memberId: Long,
        refreshToken: String,
    ): Jwt
}
