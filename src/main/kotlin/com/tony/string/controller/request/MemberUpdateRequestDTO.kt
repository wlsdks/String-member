package com.tony.string.controller.request

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class MemberUpdateRequestDTO(
    val id: Long?,

    @field:NotBlank(message = "유저 이름은 필수입니다.")
    val username: String,

    @field:NotBlank(message = "이메일은 필수입니다.")
    val email: String,

    val password: String,
    val information: String?,
    val profileImageUrl: String?,
    val location: String?,
    val birthDate: LocalDateTime?,
)
