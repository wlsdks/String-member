package com.tony.string.domain

import jakarta.persistence.*

@Entity
@Table(name = "guild")
data class Guild(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,

) : BaseEntity()
