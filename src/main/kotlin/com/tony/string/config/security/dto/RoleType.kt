package com.tony.string.config.security.dto

/**
 * 사용자 권한에 사용되는 ENUM 객체
 */
enum class RoleType(
    private val roleName: String
) {
    ADMIN("ROLE_ADMIN"),  // 관리자
    MEMBER("ROLE_USER");  // 일반 사용자

    fun getRoleName(): String = roleName
}