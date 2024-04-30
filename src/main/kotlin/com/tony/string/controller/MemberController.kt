package com.tony.string.controller

import com.tony.string.config.security.role.UserAuthorize
import com.tony.string.controller.request.MemberUpdateRequestDTO
import com.tony.string.controller.response.ApiResponse
import com.tony.string.service.impl.MemberServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@Tag(name = "로그인한 유저만 사용할 수 있는 API")
@UserAuthorize // 유저 권한 검증 (커스텀 어노테이션)
@RequestMapping("/member")
@RestController
class MemberController(
    private val memberServiceImpl: MemberServiceImpl
) {

    // 여기서 User 객체는 앞서 JwtAuthenticationFilter에서 토큰을 토대로 생성하여 시큐리티 컨텍스트에 인증 정보로 설정한 그 User 객체다.
    @Operation(summary = "회원 정보 조회")
    @GetMapping("/info")
    fun getMemberInfo(@AuthenticationPrincipal user: User) =
        ApiResponse.success(memberServiceImpl.getMemberInfo(user.username))

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/delete")
    fun deleteMember(@AuthenticationPrincipal user: User) =
        ApiResponse.success(memberServiceImpl.deleteMember(user.username))

    @Operation(summary = "회원 정보 수정")
    @PutMapping("/update")
    fun updateMember(@AuthenticationPrincipal user: User, @RequestBody request: MemberUpdateRequestDTO) =
        ApiResponse.success(memberServiceImpl.updateMember(user.username, request))

}