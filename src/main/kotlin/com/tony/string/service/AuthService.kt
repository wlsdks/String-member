package com.tony.string.service

import com.tony.string.config.security.dto.LogoutDTO

interface AuthService {
    fun logout(logoutDTO: LogoutDTO)
    fun deactivateMember(memberId: Long): Long?
}