package com.tony.string.domain

import jakarta.persistence.*

@Entity
@Table(name = "media")
data class Media(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post,

    @Column(name = "file_url", nullable = false)
    var fileUrl: String,

    @Column(name = "media_type", nullable = false)
    var mediaType: String,

    ) : BaseEntity()
