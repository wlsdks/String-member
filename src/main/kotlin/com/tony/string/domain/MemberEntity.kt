package com.tony.string.domain

import com.tony.string.config.security.dto.MemberStatus
import com.tony.string.config.security.dto.RoleType
import com.tony.string.controller.request.MemberUpdateRequestDTO
import com.tony.string.controller.request.SignUpRequestDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@Entity
@Table(name = "member")
data class MemberEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "username", nullable = false)
    var username: String,

    @Column(name = "nickname", nullable = false, unique = true)
    var nickname: String,

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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: MemberStatus? = MemberStatus.ACTIVE,

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    val roleType: RoleType? = RoleType.MEMBER,

) : BaseEntity() {
    // static 함수는 companion object 안에 정의한다.
    companion object {
        // 파라미터에 PasswordEncoder 추가
        fun fromDto(dto: SignUpRequestDTO, encoder: PasswordEncoder, ) = MemberEntity(
            username = dto.username,
            nickname = dto.nickname,
            email = dto.email,
            password = encoder.encode(dto.password),
            information = dto.information ?: "",
            profileImageUrl = dto.profileImageUrl,
            location = dto.location,
            birthDate = dto.birthDate,
            status = MemberStatus.ACTIVE
        )
    }

    fun change(dto: MemberUpdateRequestDTO) {
        username = dto.username
        email = dto.email
        information = dto.information ?: ""
        profileImageUrl = dto.profileImageUrl
        location = dto.location
        birthDate = dto.birthDate
    }

    fun passwordChange(encoder: PasswordEncoder, password: String, ) {
        this.password = encoder.encode(password)
    }
}
