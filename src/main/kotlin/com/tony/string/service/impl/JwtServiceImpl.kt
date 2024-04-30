package com.tony.string.service.impl

import com.tony.string.config.security.dto.JwtDTO
import com.tony.string.config.security.dto.JwtMemberInfoDTO
import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.config.security.jwt.JwtUtils
import com.tony.string.domain.JwtEntity
import com.tony.string.logger
import com.tony.string.repository.JwtRepository
import com.tony.string.repository.MemberRepository
import com.tony.string.service.JwtService
import org.springframework.data.util.Pair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Service
class JwtServiceImpl(
    private val jwtRepository: JwtRepository,
    private val memberRepository: MemberRepository,
    private val jwtUtils: JwtUtils
) : JwtService {

    val log = logger()

    // 로그인 여부 확인
    override fun isLoggedIn(memberId: Long): Boolean =
        memberRepository.existsById(memberId)

    // refresh token을 DB에 저장
    override fun insertRefreshTokenToDB(email: String, jwtPair: Pair<String, LocalDateTime>) {
        val member = memberRepository.findMemberByEmail(email)
        val jwt = JwtEntity.of(member.id, jwtPair.first, jwtPair.second)
        jwtRepository.save(jwt)
    }

    // access token 재발급
    override fun republishAccessToken(jwtDTO: JwtDTO): String? {
        val jwtEntity = jwtRepository.findJwtEntityByMemberIdAndRefreshToken(jwtDTO.memberId, jwtDTO.refreshToken)
        val validJwt = JwtEntity.isRefreshTokenValid(LocalDateTime.now(), jwtEntity.expiredDateTime)

        if (!validJwt) throw IllegalArgumentException("Refresh token is not valid")

        val member = memberRepository.findMemberByIdAndStatus(jwtDTO.memberId, MemberStatus.ACTIVE)
        val jwtMemberInfoDTO = JwtMemberInfoDTO.of(
            member.id,
            member.email,
            member.username,
            member.nickname,
            member.status,
            member.roleType
        )

        return jwtUtils.generateAccessToken(jwtMemberInfoDTO)
    }

}
