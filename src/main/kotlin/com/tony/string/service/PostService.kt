package com.tony.string.service

import com.tony.string.controller.request.CreatePostRequestDTO
import com.tony.string.domain.Post

interface PostService {
    fun createPost(createValidPostRequestDTO: CreatePostRequestDTO): Post
}
