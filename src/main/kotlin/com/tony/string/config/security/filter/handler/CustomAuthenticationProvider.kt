package com.tony.string.config.security.filter.handler

import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.config.security.dto.UserDetailsDto
import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component


/**
 * 3. '인증' 제공자로 사용자의 이름과 비밀번호가 요구된다.
 * 이 메서드는 사용자 정의 인증 제공자를 생성한다. 인증 제공자는 사용자 이름과 비밀번호를 사용하여 인증을 수행한다.
 * 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
 */
@Component
class CustomAuthenticationProvider(
    private val userDetailsService: UserDetailsService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : AuthenticationProvider {

    /**
     * 메인 인증 클래스
     */
    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        // 사용자 로그인 정보 추출
        val token = authentication as UsernamePasswordAuthenticationToken
        val userDetails = fetchUserDetails(token.name)

        // 유효성 검증단계
        validateUserStatus(userDetails)
        validatePassword(token.credentials as String, userDetails)

        return createSuccessAuthentication(userDetails, userDetails)
    }

    // DB에서 사용자 정보 조회하여 UserDetailsDto로 반환
    private fun fetchUserDetails(username: String): UserDetailsDto {
        return userDetailsService.loadUserByUsername(username) as UserDetailsDto
    }

    // 사용자가 상태(계정상태) 검증
    private fun validateUserStatus(userDetails: UserDetailsDto) {
        when (userDetails.getJwtMemberInfoDTO().memberStatus) {
            MemberStatus.DORMANT -> throw DisabledException("휴면 계정입니다")
            MemberStatus.DEACTIVATED -> throw AccountExpiredException("탈퇴한 계정입니다")
            else -> { /* 계정 상태 정상 */ }
        }
    }

    // 암호화된 비밀번호 비교
    private fun validatePassword(providedPassword: String, userDetails: UserDetailsDto) {
        if (!bCryptPasswordEncoder.matches(providedPassword, userDetails.getJwtMemberInfoDTO().password)) {
            throw BadCredentialsException("유저를 찾을 수 없습니다.")
        }
    }

    // 인증 성공 시 반환 객체 생성 및 반환
    private fun createSuccessAuthentication(credentials: Any, userDetails: UserDetailsDto): Authentication {
        return UsernamePasswordAuthenticationToken(
            userDetails,
            credentials,
            userDetails.authorities
        )
    }

    /**
     * 현재 제공자가 지원하는 인증 타입인지 확인.
     *
     * @param authentication 인증 타입 클래스
     * @return 지원하는 경우 true, 그렇지 않으면 false.
     */
    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}