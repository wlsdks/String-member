package com.tony.string.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "oauth")
data class OAuth(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @Column(name = "provider", nullable = false)
    var provider: String,

    @Column(name = "provider_user_id", nullable = false)
    var providerUserId: String,

    @Column(name = "access_token", nullable = false)
    var accessToken: String,

    @Column(name = "refresh_token", nullable = false)
    var refreshToken: String,

    @Column(name = "expired_at", nullable = false)
    var expiredAt: LocalDateTime,

)
