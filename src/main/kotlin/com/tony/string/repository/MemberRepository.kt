package com.tony.string.repository

import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findMemberByUsername(username: String?) : Member
    fun existsMemberByEmailAndStatus(email: String, active: MemberStatus): Boolean
    fun findMemberByEmail(email: String): Member
}