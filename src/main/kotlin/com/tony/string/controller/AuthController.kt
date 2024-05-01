package com.tony.string.controller

import com.tony.string.common.SecurityUtils
import com.tony.string.config.security.jwt.JwtUtils
import com.tony.string.controller.response.ResponseDTO
import com.tony.string.domain.dto.LogoutDTO
import com.tony.string.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
    private val jwtUtils: JwtUtils,
    private val securityUtils: SecurityUtils,
) {
    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<ResponseDTO> {
        val logout = extractLogoutDetailsFromRequest(request)
        authService.logout(logout)
        return ResponseEntity.ok(ResponseDTO.success())
    }

    /**
     * header에서 access token 추출하고 Logout 도메인 반환
     */
    private fun extractLogoutDetailsFromRequest(request: HttpServletRequest): LogoutDTO {
        val authorizationHeaderValue = request.getHeader("Authorization")
        val accessToken = jwtUtils.extractAccessToken(authorizationHeaderValue)

        return LogoutDTO.of(securityUtils.getCurrentMemberId(), accessToken!!)
    }
}
