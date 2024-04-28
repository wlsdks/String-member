package com.tony.string.config.security.dto

/**
 * 사용자 상태 관리에 사용되는 ENUM 객체
 */
enum class MemberStatus {
    ACTIVE,         // 정상
    DORMANT,        // 휴면
    DEACTIVATED     // 탈퇴
}