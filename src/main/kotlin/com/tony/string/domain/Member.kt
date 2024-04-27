package com.tony.string.domain

import com.tony.string.controller.request.SignUpRequestDTO
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder
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

) : BaseEntity() {

    //static 함수는 companion object 안에 정의한다.
    companion object {
        // 파라미터에 PasswordEncoder 추가
        fun fromDto(dto: SignUpRequestDTO, encoder: PasswordEncoder) = Member(
            username = dto.username,
            email = dto.email,
            password = encoder.encode(dto.password), //  비밀번호가 반드시 암호화된다.
            information = dto.information ?: "",
            profileImageUrl = dto.profileImageUrl,
            location = dto.location,
            birthDate = dto.birthDate
        )
    }
}