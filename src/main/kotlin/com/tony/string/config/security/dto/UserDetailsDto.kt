package com.tony.string.config.security.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsDto(
    private val jwtMemberInfoDTO: JwtMemberInfoDTO,
    private val authorities: Collection<GrantedAuthority?>
) : UserDetails {

    fun getJwtMemberInfoDTO(): JwtMemberInfoDTO {
        return jwtMemberInfoDTO
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return listOf(SimpleGrantedAuthority(jwtMemberInfoDTO.roleType.toString()))
    }

    override fun getPassword(): String? {
        return jwtMemberInfoDTO.password
    }

    override fun getUsername(): String {
        return jwtMemberInfoDTO.email
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return false
    }
}