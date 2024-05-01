package com.tony.string.service

import com.tony.string.controller.request.MemberUpdateRequestDTO
import com.tony.string.controller.request.SignUpRequestDTO
import com.tony.string.domain.MemberEntity

interface MemberService {
    fun getMemberInfo(username: String): MemberEntity

    fun save(signUpRequestDTO: SignUpRequestDTO)

    fun deleteMember(username: String?)

    fun updateMember(
        username: String?,
        requestDTO: MemberUpdateRequestDTO,
    ): MemberEntity
}
