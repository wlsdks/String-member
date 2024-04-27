package com.tony.string.controller

import com.tony.string.controller.request.SignUpRequestDTO
import com.tony.string.service.MemberService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원가입 API")
@RequestMapping("/member")
@RestController
class SignUpController(
    private val memberService: MemberService
) {

    @PostMapping("/signUp")
    fun createMember(@RequestBody signUpRequestDTO: SignUpRequestDTO) {
        memberService.save(signUpRequestDTO)
    }

}