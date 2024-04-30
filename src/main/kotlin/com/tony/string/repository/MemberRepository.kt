package com.tony.string.repository

import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.domain.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<MemberEntity, Long> {

    fun findMemberByUsername(username: String?) : MemberEntity

    fun existsMemberByEmailAndStatus(email: String, active: MemberStatus): Boolean

    fun findMemberByEmail(email: String): MemberEntity

    fun findMemberByIdAndStatus(memberId: Long, active: MemberStatus): MemberEntity

}