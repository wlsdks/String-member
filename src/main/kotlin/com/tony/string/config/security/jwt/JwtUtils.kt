package com.tony.string.config.security.jwt

import com.tony.string.config.security.dto.JwtMemberInfoDTO
import com.tony.string.logger
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.data.util.Pair
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.Key
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * JWT 생성 및 복호화를 담당하는 클래스
 */
@PropertySource("classpath:jwt.yml")
@Service
class JwtUtils() {
    val log = logger()

    @Value("\${secret-key}")
    private lateinit var secretKeyString: String

    @Value("\${expiration-hours}")
    private val expirationHours: Long = 0

    @Value("\${issuer}")
    private val issuer: String = ""

    private val secretKey: Key
        get() = Keys.hmacShaKeyFor(secretKeyString.toByteArray(StandardCharsets.UTF_8))

    companion object JwtConfig {
        const val ACCESS_TOKEN_EXPIRATION_SECONDS = 30 * 60L // 30분
        const val REFRESH_TOKEN_EXPIRATION_SECONDS = 60 * 60 * 24 * 30 * 6L // 6개월
        // const val ACCESS_TOKEN_EXPIRATION_SECONDS = 30 * 24 * 60 * 60 // 30일
        // const val ACCESS_TOKEN_EXPIRATION_SECONDS = 60 * 60 * 24 * 30 // 개발용 1개월
    }

    /**
     * 사용자 pk를 기준으로 Access Token을 발급하여 반환해 준다.
     */
    fun generateAccessToken(jwtMemberInfoDTO: JwtMemberInfoDTO): String {
        val jwtPair = generateToken(jwtMemberInfoDTO, ACCESS_TOKEN_EXPIRATION_SECONDS, "access")
        return jwtPair.first
    }

    /**
     * Authorization 헤더의 key값에서 'Bearer '를 제거하고 순수 access token만 추출한다.
     * @return access token
     */
    fun extractAccessToken(authorizationHeaderValue: String?): String? {
        return if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer ")) {
            authorizationHeaderValue.substring(7) // Remove "Bearer " prefix
        } else {
            null
        }
    }

    /**
     * 사용자 pk를 기준으로 Refresh Token을 발급하여 반환해 준다.
     * 이때 Refresh Token은 DB에 저장해야 한다.
     */
    fun generateRefreshToken(jwtMemberInfoDTO: JwtMemberInfoDTO): Pair<String, LocalDateTime> =
        generateToken(
            jwtMemberInfoDTO,
            REFRESH_TOKEN_EXPIRATION_SECONDS,
            "refresh"
        )

    /**
     * Pair라는 객체는 두 개의 연관된 값을 함께 그룹화하는데 사용
     * org.springframework.data.util.Pair 사용
     */
    private fun generateToken(
        jwtMemberInfoDTO: JwtMemberInfoDTO,
        expirationSeconds: Long,
        tokenType: String,
    ): Pair<String, LocalDateTime> {
        val expiryDate = LocalDateTime.now().plusSeconds(expirationSeconds)
        val jwtBuilder =
            Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(jwtMemberInfoDTO, tokenType))
                .setSubject(jwtMemberInfoDTO.email)
                .setIssuer(issuer)
                .setExpiration(Date.from(expiryDate.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS256)

        log.info("generateJwtToken - Token generated for user email: " + jwtMemberInfoDTO.email)
        return Pair.of(jwtBuilder.compact(), expiryDate)
    }

    /**
     * 사용자 정보를 기반으로 클래임을 생성
     */
    private fun createClaims(
        jwtMemberInfoDTO: JwtMemberInfoDTO,
        tokenType: String,
    ): Map<String, Any?> {
        val claims: MutableMap<String, Any?> = HashMap()
        claims["memberId"] = jwtMemberInfoDTO.memberId
        claims["email"] = jwtMemberInfoDTO.email
        claims["nickname"] = jwtMemberInfoDTO.nickname
        claims["type"] = tokenType // Token 종류를 저장
        claims["role"] = jwtMemberInfoDTO.roleType?.name
        return claims
    }

    /**
     * 토큰 정보를 기반으로 Claims 정보를 반환받는 메서드
     */
    fun getClaimsFromToken(token: String?): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    /**
     * JWT의 헤더값을 생성해주는 메서드
     */
    private fun createHeader(): Map<String, Any> {
        val header: MutableMap<String, Any> = HashMap()
        header["typ"] = "JWT"
        header["alg"] = "HS256"
        header["regDate"] = System.currentTimeMillis()
        return header
    }

    fun getMemberIdFromToken(token: String?): Long {
        val claims = getClaimsFromToken(token)
        return claims.get("memberId", Long::class.java)
    }

    fun getNicknameFromToken(token: String?): String {
        val claims = getClaimsFromToken(token)
        return claims.get("nickname", String::class.java)
    }

    fun getEmailFromToken(token: String?): String {
        val claims = getClaimsFromToken(token)
        return claims.get("email", String::class.java)
    }

    fun getRoleFromToken(token: String?): String {
        val claims = getClaimsFromToken(token)
        return claims.get("role", String::class.java)
    }
}
