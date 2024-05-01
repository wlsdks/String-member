package com.tony.string.controller.request

import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.domain.MemberEntity
import java.time.LocalDateTime

/**
 * 회원가입 요청 DTO
 */
data class SignUpRequestDTO(
    val id: Long?,
    val username: String,
    val nickname: String,
    val email: String,
    val password: String,
    val information: String?,
    val profileImageUrl: String?,
    val location: String?,
    val birthDate: LocalDateTime?,
) {
    fun toEntity(): MemberEntity {
        return MemberEntity(
            id = id ?: 0,
            username = username,
            nickname = nickname,
            email = email,
            password = password,
            information = information ?: "",
            profileImageUrl = profileImageUrl,
            location = location,
            birthDate = birthDate,
            status = MemberStatus.ACTIVE
        )
    }
}
