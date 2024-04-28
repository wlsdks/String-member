package com.tony.string.service.impl

import com.tony.string.config.security.dto.domain.Jwt
import com.tony.string.service.JwtService
import org.springframework.data.util.Pair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Service
class JwtServiceImpl() : JwtService {

    override fun isLoggedIn(memberId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun insertRefreshTokenToDB(email: String, jwtPair: Pair<String, LocalDateTime>) {
        TODO("Not yet implemented")
    }

    override fun republishAccessToken(jwt: Jwt): String? {
        TODO("Not yet implemented")
    }

}