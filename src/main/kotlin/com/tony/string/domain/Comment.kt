package com.tony.string.domain

import jakarta.persistence.*

@Entity
@Table(name = "comment")
data class Comment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    var member: Member,

    @Column(name = "content", nullable = false)
    var content: String,

    ) : BaseEntity()
