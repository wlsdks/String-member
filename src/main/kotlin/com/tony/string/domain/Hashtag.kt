package com.tony.string.domain

import jakarta.persistence.*

@Entity
@Table(name = "hashtag")
data class Hashtag(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "name", nullable = false)
    val name: String

)
