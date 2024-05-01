package com.tony.string.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용. 상속받는 클래스에서 createDtm, modifyDtm도 컬럼으로 인식
@EntityListeners(value = [AuditingEntityListener::class]) // Entity에서 이벤트가 발생할 때마다 Auditing 수행
abstract class BaseEntity(

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected var createdAt: LocalDateTime = LocalDateTime.MIN,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    protected var updatedAt: LocalDateTime = LocalDateTime.MIN,

)
