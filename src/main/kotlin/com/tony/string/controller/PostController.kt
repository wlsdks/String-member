package com.tony.string.controller

import com.tony.string.config.security.role.UserAuthorize
import com.tony.string.controller.request.CreatePostRequestDTO
import com.tony.string.controller.response.ResponseDTO
import com.tony.string.service.PostService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "게시글 API")
@UserAuthorize
@RequestMapping("/post")
@RestController
class PostController(
    private val postService: PostService
) {

    @PostMapping("/create")
    fun createPost(@Valid @RequestBody requestDTO: CreatePostRequestDTO): ResponseEntity<ResponseDTO> {
        val post = postService.createPost(requestDTO)
        return ResponseEntity.ok(ResponseDTO.success(post))
    }

}
