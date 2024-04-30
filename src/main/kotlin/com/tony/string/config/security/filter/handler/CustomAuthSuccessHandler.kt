package com.tony.string.config.security.filter.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.tony.string.config.security.dto.UserDetailsDto
import com.tony.string.config.security.jwt.JwtUtils
import com.tony.string.controller.response.ResponseDTO
import com.tony.string.service.AuthService
import com.tony.string.service.JwtService
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.util.Pair
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime

/**
 * 4. Spring Security 기반의 사용자의 정보가 맞을 경우 수행이 되며 결과값을 리턴해주는 Handler
 * customLoginSuccessHandler: 이 메서드는 인증 성공 핸들러를 생성한다. 인증 성공 핸들러는 인증 성공시 수행할 작업을 정의한다.
 */
@Component
class CustomAuthSuccessHandler(
    private val objectMapper: ObjectMapper,
    private val jwtUtils: JwtUtils,
    private val jwtService: JwtService,
    private val authService: AuthService
) : SavedRequestAwareAuthenticationSuccessHandler() {

    @Throws(ServletException::class, IOException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        // 사용자 정보 가져오기
        val userDetails = authentication.principal as UserDetailsDto
        handleAuthenticationSuccess(userDetails, response)
    }

    // 로그인 성공 시 처리
    private fun handleAuthenticationSuccess(userDetails: UserDetailsDto, response: HttpServletResponse) {
        // JWT 토큰 생성 (Dto를 받아서 그 정보로 토큰 생성)
        val jwtMemberInfo = userDetails.getJwtMemberInfoDTO()
        val accessToken = jwtUtils.generateAccessToken(jwtMemberInfo)
        val refreshTokenPair = jwtUtils.generateRefreshToken(jwtMemberInfo)

        // todo: 이미 로그인 되어있는지 확인하는 메서드가 필요한가? 로그인 되어있으면 로그아웃시키고 ?? 어카지 refresh 발급도 해야하는데
        storeRefreshToken(jwtMemberInfo.email, refreshTokenPair)
        writeResponse(response, jwtMemberInfo.memberId, accessToken, refreshTokenPair.first)
    }

    // Refresh Token DB에 저장 : 로그인 처리 진행
    private fun storeRefreshToken(email: String, refreshTokenPair: Pair<String, LocalDateTime>) {
        jwtService.insertRefreshTokenToDB(email, refreshTokenPair)
    }

    // 응답 작성
    private fun writeResponse(
        response: HttpServletResponse,
        memberId: Long,
        accessToken: String,
        refreshToken: String
    ) {
        val tokenMap = mutableMapOf<String, Any>(
            "memberId" to memberId,
            "accessToken" to accessToken,
            "refreshToken" to refreshToken
        )
        val responseDto = ResponseDTO.success(tokenMap)

        response.characterEncoding = "UTF-8"
        response.contentType = "application/json"
        response.writer.use { it.print(objectMapper.writeValueAsString(responseDto)) }
    }
}