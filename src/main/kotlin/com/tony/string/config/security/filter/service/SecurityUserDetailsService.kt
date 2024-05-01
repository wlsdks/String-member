package com.tony.string.config.security.filter.service

import com.tony.string.config.security.dto.JwtMemberInfoDTO
import com.tony.string.config.security.dto.UserDetailsDto
import com.tony.string.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * 스프링 시큐리티 UserDetailsService 구현체
 */
@Service
class SecurityUserDetailsService(
    private val memberRepository: MemberRepository,
) : UserDetailsService {
    /**
     * 사용자 정보를 조회하는 메서드
     */
    override fun loadUserByUsername(email: String): UserDetails {
        // 회원을 조회한 후 JwtMemberInfoDTO 객체로 변환
        val member = memberRepository.findMemberByEmail(email)
        val jwtMemberInfoDTO = JwtMemberInfoDTO.fromEntity(member)

        // 사용자 정보 기반으로 SecurityUserDetailsDto 객체 생성
        return UserDetailsDto(
            jwtMemberInfoDTO,
            setOf(SimpleGrantedAuthority(jwtMemberInfoDTO.roleType.toString()))
        )
    }
}
