package com.tony.string.controller.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class CreatePostRequestDTO(
    val id: Long?,

    @field:NotBlank(message = "제목은 필수입니다.")
    val title: String,

    @field:NotBlank(message = "내용은 필수입니다.")
    val content: String,

    @field:NotNull(message = "작성자는 필수입니다.")
    @field:Positive(message = "작성자는 양수여야 합니다.")
    val memberId: Long,

    val guildId: Long?,
)