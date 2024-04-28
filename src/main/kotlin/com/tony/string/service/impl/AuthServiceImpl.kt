package com.tony.string.service.impl

import com.tony.string.config.security.dto.domain.Logout
import com.tony.string.service.AuthService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AuthServiceImpl : AuthService {

    override fun logout(logout: Logout) {
        TODO("Not yet implemented")
    }

    override fun deactivateMember(memberId: Long): Long? {
        TODO("Not yet implemented")
    }

}