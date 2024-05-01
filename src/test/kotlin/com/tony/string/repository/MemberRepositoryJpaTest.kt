package com.tony.string.repository

import com.tony.string.JpaTestSupporter
import com.tony.string.domain.MemberEntity
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class MemberRepositoryJpaTest : JpaTestSupporter() {
    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    fun `멤버 이름으로 멤버 찾기`() {
        val member = createTestMemberEntity()
        memberRepository.save(member)
        val findMember = memberRepository.findMemberByUsername("tony")
        assertNotNull(findMember)
    }

    fun createTestMemberEntity(): MemberEntity {
        return MemberEntity(
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
