package com.tony.string.service

import com.tony.string.controller.request.SignUpRequestDTO
import com.tony.string.domain.Member
import com.tony.string.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val encoder: PasswordEncoder
) {

    @Transactional
    fun save(signUpRequestDTO: SignUpRequestDTO) {
        memberRepository.save(Member.fromDto(signUpRequestDTO, encoder))
    }


}