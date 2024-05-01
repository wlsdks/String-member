package com.tony.string.repository

import com.tony.string.JpaTestSupporter
import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.domain.Member
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("[JPA] MemberRepository 테스트")
class MemberRepositoryTest : JpaTestSupporter() {
    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    fun `이름으로 유저 찾기`() {
        // Given
        val member = createTestMemberEntity()
        memberRepository.save(member)

        // When
        val findMember = memberRepository.findMemberByUsername("tony")

        // Then
        assertNotNull(findMember)
    }

    @Test
    fun `이메일로 유저 찾기`() {
        // Given
        val member = createTestMemberEntity()
        memberRepository.save(member)

        // When
        val findMember = memberRepository.findMemberByEmail("tony@example.com")

        // Then
        Assertions.assertThat(findMember).isNotNull
        Assertions.assertThat(findMember.email).isEqualTo("tony@example.com")
    }

    @Test
    fun `이메일과 상태를 통해 실존하는 유저인지 검증한다`() {
        // Given
        val member = createTestMemberEntity()
        memberRepository.save(member)

        // When
        val exists = memberRepository.existsMemberByEmailAndStatus("tony@example.com", MemberStatus.ACTIVE)

        Assertions.assertThat(exists).isTrue()
    }

    @Test
    fun `아이디와 상태를 통해 실존하는 유저인지 검증한다`() {
        // Given
        val member = createTestMemberEntity()
        memberRepository.save(member)
        val savedMember = memberRepository.findMemberByUsername("tony")

        // When
        val findMember = memberRepository.findMemberByIdAndStatus(savedMember.id, MemberStatus.ACTIVE)

        // Then
        Assertions.assertThat(findMember).isNotNull
        Assertions.assertThat(findMember.email).isEqualTo("tony@example.com")
        Assertions.assertThat(findMember.nickname).isEqualTo("tonyNickname")
    }

    private fun createTestMemberEntity(): Member {
        return Member(
            username = "tony",
            nickname = "tonyNickname",
            email = "tony@example.com",
            password = "password",
            information = "Test information",
            profileImageUrl = "https://example.com/profile.jpg",
            location = "Test location",
            birthDate = null
        )
    }
}
