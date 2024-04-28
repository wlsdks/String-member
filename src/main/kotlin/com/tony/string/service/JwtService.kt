package com.tony.string.service

import com.tony.string.config.security.dto.domain.Jwt
import org.springframework.data.util.Pair
import java.time.LocalDateTime

interface JwtService {
    fun isLoggedIn(memberId: Long): Boolean
    fun insertRefreshTokenToDB(email: String, jwtPair: Pair<String, LocalDateTime>)
    fun republishAccessToken(jwt: Jwt): String?
}