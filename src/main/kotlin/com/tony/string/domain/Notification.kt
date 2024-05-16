package com.tony.string.domain

import jakarta.persistence.*

@Entity
@Table(name = "notification")
data class Notification(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @Column(name = "type", nullable = false)
    var type: String, // 예: 'like', 'comment', 'friend_request'

    @Column(name = "reference_id", nullable = false)
    var referenceId: Long, // 관련된 게시물 또는 댓글의 ID

) : BaseEntity()
