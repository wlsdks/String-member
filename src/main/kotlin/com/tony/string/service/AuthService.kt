package com.tony.string.service

interface AuthService {
    fun logout(logoutDTO: LogoutDTO)
    fun deactivateMember(memberId: Long): Long?
}