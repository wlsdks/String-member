package com.tony.string.domain

import jakarta.persistence.*

// todo: 이건 아예 다시설계해야함
@Entity
@Table(name = "friendship")
data class Friendship(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    var memberId: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    var friend: Member,

) : BaseEntity()
