package com.tony.string.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "member")
data class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "username", nullable = false, unique = true)
    var username: String,

    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "information", nullable = false)
    var information: String,

    @Column(name = "profile_image_url")
    var profileImageUrl: String?,

    @Column(name = "location")
    var location: String?,

    @Column(name = "birth_date")
    var birthDate: LocalDateTime?,

) : BaseEntity()