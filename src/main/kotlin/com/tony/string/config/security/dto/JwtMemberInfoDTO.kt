package com.tony.string.config.security.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.tony.string.domain.MemberEntity


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

        fun fromEntity(memberEntity: MemberEntity): JwtMemberInfoDTO =
            JwtMemberInfoDTO(
                memberEntity.id,
                memberEntity.email,
                memberEntity.password,
                memberEntity.nickname,
                memberEntity.status,
                memberEntity.roleType
            )

        fun of(id: Long, email: String, username: String, nickname: String, status: MemberStatus?, roleType: RoleType?) =
            JwtMemberInfoDTO(id, email, null, nickname, status, roleType)

    }

}
