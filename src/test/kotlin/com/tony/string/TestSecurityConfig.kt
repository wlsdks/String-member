package com.tony.string

import com.tony.string.config.security.filter.CustomAuthenticationFilter
import com.tony.string.config.security.filter.JwtAuthenticationFilter
import com.tony.string.config.security.filter.handler.CustomAuthFailureHandler
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Profile("test")
@Configuration
@EnableWebSecurity
class TestSecurityConfig {

    @MockBean
    private lateinit var customAuthenticationFilter: CustomAuthenticationFilter

    @MockBean
    private lateinit var customAuthFailureHandler: CustomAuthFailureHandler

    @MockBean
    private lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize.anyRequest().permitAll()
            }
            .csrf { csrf -> csrf.disable() }
        return http.build()
    }

}
