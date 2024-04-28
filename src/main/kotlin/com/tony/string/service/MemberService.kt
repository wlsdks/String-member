package com.tony.string.service

import com.tony.string.controller.request.MemberUpdateRequestDTO
import com.tony.string.controller.request.SignUpRequestDTO
import com.tony.string.domain.Member

interface MemberService {

    fun getMemberInfo(username: String): Member

    fun save(signUpRequestDTO: SignUpRequestDTO)

    fun deleteMember(username: String?)

    fun updateMember(username: String?, requestDTO: MemberUpdateRequestDTO): Member

}