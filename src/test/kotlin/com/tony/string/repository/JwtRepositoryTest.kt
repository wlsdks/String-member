package com.tony.string.repository

import com.tony.string.JpaTestSupporter
import com.tony.string.domain.Jwt
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

@DisplayName("[JPA] JwtRepository 테스트")
class JwtRepositoryTest : JpaTestSupporter() {

    @Autowired
    private lateinit var jwtRepository: JwtRepository

    @Test
    fun `유저의 id를 통해 저장된 Jwt 정보를 삭제한다`() {
        // Given
        val jwt = createTestJwtEntity()
        val savedJwt = jwtRepository.save(jwt)

        // When
        jwtRepository.deleteJwtByMemberId(savedJwt.memberId)

        // Then
        Assertions.assertThat(jwtRepository.findById(savedJwt.id!!)).isEmpty
    }

    @Test
    fun `유저의 id와 리프레시 토큰을 통해 저장된 Jwt 정보를 조회한다`() {
        // Given
        val jwt = createTestJwtEntity()
        val savedJwt = jwtRepository.save(jwt)

        // When
        val findJwt = jwtRepository.findJwtByMemberIdAndRefreshToken(savedJwt.memberId, savedJwt.refreshToken)

        // Then
        Assertions.assertThat(findJwt).isNotNull
        Assertions.assertThat(findJwt.memberId).isEqualTo(savedJwt.memberId)
        Assertions.assertThat(findJwt.refreshToken).isEqualTo(savedJwt.refreshToken)
    }

    private fun createTestJwtEntity() : Jwt {
        return Jwt(
            memberId = 1L,
            refreshToken = "refreshToken",
            expiredDateTime = LocalDateTime.now()
        )
    }
}
