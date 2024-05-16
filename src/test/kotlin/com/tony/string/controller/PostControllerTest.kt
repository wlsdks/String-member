package com.tony.string.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tony.string.TestSecurityConfig
import com.tony.string.config.security.SecurityConfig
import com.tony.string.config.security.filter.CustomAuthenticationFilter
import com.tony.string.config.security.filter.JwtAuthenticationFilter
import com.tony.string.config.security.filter.handler.CustomAuthFailureHandler
import com.tony.string.controller.request.CreatePostRequestDTO
import com.tony.string.service.PostService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [PostController::class])
@Import(TestSecurityConfig::class) // 명시적으로 TestSecurityConfig를 포함
@ActiveProfiles("test")
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var postService: PostService

    val objectMapper = ObjectMapper()


//    @WithMockUser
    @DisplayName("게시글 생성")
    @Test
    fun createPost() {
        val dto = createValidPostRequestDTO()

        mockMvc.perform(post("/post/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isOk)

    }

    fun createValidPostRequestDTO() = CreatePostRequestDTO(
        id = null,
        title = "유효한 제목",
        content = "유효한 내용",
        memberId = 1L,
        guildId = null
    )

}