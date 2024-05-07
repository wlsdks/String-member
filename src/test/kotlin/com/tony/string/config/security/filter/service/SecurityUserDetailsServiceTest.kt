package com.tony.string.config.security.filter.service

import com.tony.string.domain.Member
import com.tony.string.repository.MemberRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("[Service] SecurityUserDetailsService 테스트")
class SecurityUserDetailsServiceTest {
    @Mock
    private lateinit var memberRepository: MemberRepository

    @InjectMocks
    private lateinit var securityUserDetailsService: SecurityUserDetailsService

    @Test
    fun `사용자 정보를 정상적으로 조회하는지 테스트`() {
        // given
        val expectedEmail = "tony@example.com"
        val mockMember = createTestMemberEntity()
        `when`(memberRepository.findMemberByEmail(expectedEmail)).thenReturn(mockMember)

        // when
        val userDetails = securityUserDetailsService.loadUserByUsername(expectedEmail)

        // then
        assertNotNull(userDetails)
        assertEquals(expectedEmail, userDetails.username)
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
