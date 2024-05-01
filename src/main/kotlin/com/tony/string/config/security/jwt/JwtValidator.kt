package com.tony.string.config.security.jwt

import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.logger
import com.tony.string.repository.MemberRepository
import io.jsonwebtoken.JwtException
import org.springframework.stereotype.Component

/**
 * JWT 유효성 검증을 담당하는 클래스
 */
@Component
class JwtValidator(
    private val memberRepository: MemberRepository,
    private val jwtUtils: JwtUtils,
) {
    val log = logger()

    // 토큰이 유효한지 검증한다. (tokenType에는 access, refresh가 들어온다.)
    fun isValidToken(
        token: String?,
        tokenType: String,
    ): Boolean {
        token ?: run {
            log.debug("Token is null")
            return false
        }

        return try {
            validateTokenClaims(token, tokenType)
        } catch (e: JwtException) {
            log.debug("JWT validation error: {} for token: {}", e.message, token)
            false
        }
    }

    // 토큰의 클레임을 검증한다.
    private fun validateTokenClaims(
        token: String,
        tokenType: String,
    ): Boolean {
        val claims = jwtUtils.getClaimsFromToken(token)
        val type = claims.get("type", String::class.java)

        // access token인지 확인
        if (type != tokenType) {
            log.debug("Token type mismatch: expected $tokenType, found $type")
            return false
        }

        // 이메일 값이 null이면 run 블록을 실행한다.
        val email =
            claims.get("email", String::class.java) ?: run {
                log.debug("Email from token is null")
                return false
            }
        return checkActiveMember(email)
    }

    // 유저 상태가 활성화된 유저인지 검증한다.
    private fun checkActiveMember(email: String): Boolean {
        return memberRepository.existsMemberByEmailAndStatus(email, MemberStatus.ACTIVE)
    }
}
