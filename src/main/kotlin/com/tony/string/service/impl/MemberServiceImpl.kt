package com.tony.string.service.impl

import com.tony.string.controller.request.MemberUpdateRequestDTO
import com.tony.string.controller.request.SignUpRequestDTO
import com.tony.string.domain.Member
import com.tony.string.logger
import com.tony.string.repository.MemberRepository
import com.tony.string.service.MemberService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val encoder: PasswordEncoder,
) : MemberService {
    val log = logger()

    override fun getMemberInfo(username: String): Member {
        return memberRepository.findMemberByUsername(username)
    }

    @Transactional
    override fun save(signUpRequestDTO: SignUpRequestDTO) {
        memberRepository.save(Member.fromDto(signUpRequestDTO, encoder))
    }

    override fun deleteMember(username: String?) {
        val memberId = memberRepository.findMemberByUsername(username).id
        memberRepository.deleteById(memberId)
    }

    @Transactional
    override fun updateMember(
        username: String?,
        requestDTO: MemberUpdateRequestDTO,
    ): Member {
        val member = memberRepository.findMemberByUsername(username)
        member.change(requestDTO)
        return memberRepository.save(member)
    }
}
