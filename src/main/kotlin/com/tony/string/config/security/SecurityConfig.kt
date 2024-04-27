package com.tony.string.config.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/", "/swagger-ui/**", "/member/**", "/sign-up", "/sign-in")
                    .permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll() // H2 콘솔 접속은 모두에게 허용
                    .anyRequest().authenticated()                           // 그 외의 모든 요청은 인증 필요
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

}