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

) : BaseEntity() {

    // 펙토리 메서드 선언
    companion object {

        fun of(name: String, description: String): Guild {
            return Guild(
                name = name,
                description = description
            )
        }

        // 저장할때는 엔티티의 id값만 있으면 된다.
        fun fromId(id: Long?): Guild {
            return Guild(id = id ?: 0, name = "", description = "")
        }

    }
}