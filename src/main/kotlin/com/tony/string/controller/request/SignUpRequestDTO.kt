package com.tony.string.controller.request

import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.domain.Member
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

/**
 * 회원가입 요청 DTO
 */
data class SignUpRequestDTO(
    val id: Long?,

    @field:NotBlank(message = "유저 이름은 필수입니다.")
    val username: String,

    @field:NotBlank(message = "닉네임은 필수입니다.")
    val nickname: String,

    @field:NotBlank(message = "이메일은 필수입니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    val password: String,

    val information: String?,
    val profileImageUrl: String?,
    val location: String?,
    val birthDate: LocalDateTime?,
) {
    fun toEntity(): Member {
        return Member(
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
