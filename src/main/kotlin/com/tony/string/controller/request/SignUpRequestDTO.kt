package com.tony.string.controller.request

import com.tony.string.domain.Member
import java.time.LocalDateTime

data class SignUpRequestDTO(
    val id: Long?,
    val username: String,
    val email: String,
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
            email = email,
            password = password,
            information = information ?: "",
            profileImageUrl = profileImageUrl,
            location = location,
            birthDate = birthDate,
        )
    }
}
