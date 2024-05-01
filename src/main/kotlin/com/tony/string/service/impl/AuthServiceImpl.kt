package com.tony.string.service.impl

import com.tony.string.domain.dto.LogoutDTO
import com.tony.string.logger
import com.tony.string.repository.JwtRepository
import com.tony.string.repository.MemberRepository
import com.tony.string.repository.querydsl.MemberQuerydslRepository
import com.tony.string.service.AuthService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AuthServiceImpl(
    private val jwtRepository: JwtRepository,
    private val memberRepository: MemberRepository,
    private val memberQuerydslRepository: MemberQuerydslRepository,
) : AuthService {
    val log = logger()

    /**
     * 로그아웃 - 리프레시 토큰을 DB에서 제거
     */
    @Transactional
    override fun logout(logoutDTO: LogoutDTO) {
        // db에서 refresh token 삭제
        jwtRepository.deleteJwtEntityByMemberId(logoutDTO.memberId)

        // token blacklist에 추가
//        jwtRepository.insertTokenBlacklist();
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    override fun deactivateMember(memberId: Long): Long? {
        return memberQuerydslRepository.deactivateMember(memberId)
    }
}
