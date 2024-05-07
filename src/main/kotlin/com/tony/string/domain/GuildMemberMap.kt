package com.tony.string.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "guild_member_map")
data class GuildMemberMap(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    var group: Guild,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    var member: Member,

    @Column(name = "role", nullable = false)
    var role: String,

    @Column(name = "joined_at", nullable = false)
    var joinedAt: LocalDateTime,

    ) : BaseEntity()
