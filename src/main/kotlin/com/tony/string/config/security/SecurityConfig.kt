package com.tony.string.config.security

import com.tony.string.config.security.filter.CustomAuthenticationFilter
import com.tony.string.config.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

// 메소드 시큐리티를 활성화
@EnableMethodSecurity
@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val entryPoint: AuthenticationEntryPoint,
) {
    private val allowedUrls = arrayOf("/", "/swagger-ui/**", "/member/**", "/auth/**")

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun filterChain(
        http: HttpSecurity,
        customAuthenticationFilter: CustomAuthenticationFilter,
    ): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .cors { it.disable() } // cors 설정을 해제 (모바일이면 필요없음)
            .authorizeHttpRequests {
                it.requestMatchers(*allowedUrls).permitAll()
                    .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter::class.java)
            .addFilterBefore(customAuthenticationFilter, jwtAuthenticationFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(entryPoint) } // 중앙 집중 예외 처리를 위한 추가
            .build()
    }
}
