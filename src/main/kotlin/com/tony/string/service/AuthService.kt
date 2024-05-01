package com.tony.string.service

import com.tony.string.domain.dto.LogoutDTO

interface AuthService {
    fun logout(logoutDTO: LogoutDTO)

    fun deactivateMember(memberId: Long): Long?
}
