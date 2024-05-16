package com.tony.string.domain

import com.tony.string.controller.request.CreatePostRequestDTO
import jakarta.persistence.*

@Entity
@Table(name = "post")
data class Post(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guild_id", nullable = false)
    var guild: Guild,

) : BaseEntity() {

    // 펙토리 메서드 선언
    companion object {

        fun of(title: String, content: String, member: Member, guild: Guild): Post {
            return Post(
                title = title,
                content = content,
                member = member,
                guild = guild
            )
        }

        // 저장할때는 엔티티의 id값만 있으면 된다.
        fun fromDto(createPostRequestDTO: CreatePostRequestDTO): Post {
            return Post(
                title = createPostRequestDTO.title,
                content = createPostRequestDTO.content,
                member = Member.fromId(id = createPostRequestDTO.memberId),
                guild = Guild.fromId(id = createPostRequestDTO.guildId)
            )
        }
    }

}

