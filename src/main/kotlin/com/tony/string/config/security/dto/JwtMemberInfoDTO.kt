package com.tony.string.config.security.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.tony.string.domain.Member


/**
 * Security JWT인증 객체
 */
data class JwtMemberInfoDTO(
    @JsonProperty("id") val id: Long,
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String? = null,
    @JsonProperty("nickname") val nickname: String? = null,
    @JsonProperty("memberStatus") val memberStatus: MemberStatus? = null,
    @JsonProperty("roleType") val roleType: RoleType? = null
) {

    // static 펙토리 메서드
    companion object {

        fun fromDto(id: Long, email: String): JwtMemberInfoDTO =
            JwtMemberInfoDTO(id, email)

        fun fromEntity(member: Member): JwtMemberInfoDTO =
            JwtMemberInfoDTO(member.id, member.email, member.password, member.nickname, member.status, member.roleType)

    }

}
