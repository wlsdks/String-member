package com.tony.string.common

import com.tony.string.config.security.dto.JwtMemberInfoDTO
import com.tony.string.exception.CustomException
import com.tony.string.exception.ErrorCode
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityUtils {

    // 현재 인증된 사용자의 TokenMemberInfoDto를 가져온다.
    fun getCurrentTokenMemberInfoDto(): JwtMemberInfoDTO {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw CustomException(ErrorCode.AUTHENTICATION_NOT_FOUND, "인증 정보를 찾을 수 없습니다.")

        if (!authentication.isAuthenticated) {
            throw CustomException(ErrorCode.UNAUTHENTICATED, "사용자가 인증되지 않았습니다.")
        }

        /**
         * JwtAuthenticationFilter에서 SecurityContextHolder에 UsernamePasswordAuthenticationToken()를 설정하는데
         * 토큰 생성자의 첫번째 파라미터가 principal이다. 이것을 authentication.principal로 접근하여 가져온다.
         */
        val jwtMemberInfoDTO = authentication.principal
        if (jwtMemberInfoDTO !is JwtMemberInfoDTO) {
            throw CustomException(
                ErrorCode.PRINCIPAL_NOT_CORRECT_TYPE,
                "시큐리티의 Principle 타입이 JwtMemberInfoDTO 타입이 아닙니다."
            )
        }

        return jwtMemberInfoDTO
    }

    // 현재 인증된 사용자의 memberId를 가져온다.
    fun getCurrentMemberId(): Long = getCurrentTokenMemberInfoDto().memberId

    // 현재 인증된 사용자의 email을 가져온다.
    fun getCurrentMemberEmail(): String = getCurrentTokenMemberInfoDto().email

}