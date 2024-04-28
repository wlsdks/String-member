package com.tony.string.controller.request

import java.time.LocalDateTime

data class MemberUpdateRequestDTO(
    val id: Long?,
    val username: String,
    val email: String,
    val password: String,
    val information: String?,
    val profileImageUrl: String?,
    val location: String?,
    val birthDate: LocalDateTime?,
)
