package com.tony.string.service

import com.tony.string.config.security.dto.domain.Logout

interface AuthService {
    fun logout(logout: Logout)
    fun deactivateMember(memberId: Long): Long?
}