package com.tony.string.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_setting")
data class UserSetting(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @Column(name = "setting", nullable = false)
    var setting: String,

    @Column(name = "value", nullable = false)
    var value: String,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime,

)
