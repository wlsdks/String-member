package com.tony.string.config.security.dto.domain

data class Logout(
    val memberId: Long,
    val accessToken: String
)
