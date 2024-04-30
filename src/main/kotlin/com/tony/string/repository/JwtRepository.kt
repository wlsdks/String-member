package com.tony.string.repository

import com.tony.string.domain.JwtEntity
import org.springframework.data.jpa.repository.JpaRepository

interface JwtRepository : JpaRepository<JwtEntity, Long> {
    fun deleteJwtEntityByMemberId(memberId: Long)
    fun findJwtEntityByMemberIdAndRefreshToken(memberId: Long, refreshToken: String): JwtEntity
}