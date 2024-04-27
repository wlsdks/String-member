package com.tony.string.controller

import com.tony.string.controller.request.SignUpRequestDTO
import com.tony.string.service.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/create")
    fun createMember(@RequestBody signUpRequestDTO: SignUpRequestDTO) {
        memberService.save(signUpRequestDTO)
    }

}