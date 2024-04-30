package com.tony.string.repository

import com.tony.string.domain.MemberEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource


// 참고: https://0soo.tistory.com/40
@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = ["classpath:application-test.yml"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 이 설정은 필수다.
class MemberRepositoryTest {

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
            birthDate = null // 생년월일을 특정하지 않는다면 null로 설정할 수 있습니다.
        )
    }

}
