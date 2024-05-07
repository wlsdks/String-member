package com.tony.string.config.security.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.tony.string.domain.Member

/**
 * Security JWT인증 객체
 */
data class JwtMemberInfoDTO(
    @JsonProperty("memberId") val memberId: Long,
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String? = null,
    @JsonProperty("nickname") val nickname: String? = null,
    @JsonProperty("memberStatus") val memberStatus: MemberStatus? = null,
    @JsonProperty("roleType") val roleType: RoleType? = null,
) {
    // static 펙토리 메서드
    companion object {
        fun fromDto(
            memberId: Long,
            email: String,
        ): JwtMemberInfoDTO = JwtMemberInfoDTO(memberId, email)

        fun fromEntity(member: Member): JwtMemberInfoDTO =
            JwtMemberInfoDTO(
                member.id,
                member.email,
                member.password,
                member.nickname,
                member.status,
                member.roleType
            )

        fun of(
            memberId: Long,
            email: String,
            username: String,
            nickname: String,
            status: MemberStatus?,
            roleType: RoleType?,
        ) = JwtMemberInfoDTO(memberId, email, username, nickname, status, roleType)
    }
}
