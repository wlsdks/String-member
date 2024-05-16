package com.tony.string.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "activity_log")
data class ActivityLog(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @Column(name = "action", nullable = false)
    var action: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "occurred_at", nullable = false)
    var occurredAt: LocalDateTime = LocalDateTime.now(),

)
