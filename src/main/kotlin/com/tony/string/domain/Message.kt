package com.tony.string.domain

import jakarta.persistence.*

//todo: 여기서도 sender가 sender_id로 되어있는데 어떻게 해야할지 알아보기
@Entity
@Table(name = "message")
data class Message(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    var sender: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    var receiver: Member,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "read", nullable = false)
    var read: Boolean = false,

) : BaseEntity()
