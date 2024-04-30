package com.tony.string.config.security.dto

data class LogoutDTO(
    val memberId: Long,
    val accessToken: String
)
