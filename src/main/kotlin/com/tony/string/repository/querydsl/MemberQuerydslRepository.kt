package com.tony.string.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.domain.QMemberEntity.memberEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MemberQuerydslRepository(
    private val jpaQueryFactory: JPAQueryFactory
) {

    // 회원 탈퇴 (soft delete: 1주일간은 deactive 상태로 유지하고 그 이후에 삭제)
    fun deactivateMember(memberId: Long): Long? {
        return jpaQueryFactory
            .update(memberEntity)
            .set(memberEntity.status, MemberStatus.DEACTIVATED)
            .set(memberEntity.updatedAt, LocalDateTime.now())
            .where(memberEntity.id.eq(memberId))
            .execute()
    }

}