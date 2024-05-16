package com.tony.string.service.impl

import com.tony.string.controller.request.CreatePostRequestDTO
import com.tony.string.domain.Guild
import com.tony.string.domain.Member
import com.tony.string.domain.Post
import com.tony.string.exception.CustomException
import com.tony.string.repository.GuildRepository
import com.tony.string.repository.MemberRepository
import com.tony.string.repository.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@DisplayName("PostServiceImpl 통합 테스트")
@SpringBootTest
class PostServiceImplTest(
    private val postService: PostServiceImpl,
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
    private val guildRepository: GuildRepository
) : BehaviorSpec({

    given("댓글 생성시") {
        // 필요한 데이터를 저장한다.
        memberRepository.save(createMember())
        guildRepository.save(createGuild())

        When("인풋이 정상적으로 들어오면") {
            postService.createPost(createValidPostRequestDTO())

            then("정상 생성됨을 확인한다.") {
                val post = postRepository.findByIdOrNull(1L)
                post shouldNotBe null
                post?.content shouldBe "게시글 내용"
                post?.member?.nickname shouldBe "tony"
            }
        }
        When("작성한 유저가 존재하지 않으면") {
            then("작성한 유저가 존재하지 않는 유저이면 예외가 발생한다.") {
                shouldThrow<CustomException> {
                    postService.createPost(createNotValidPostRequestDTO())
                }
            }
        }
    }

})

// 외부에 노출되는 함수를 만들어서 테스트에서 사용할 수 있도록 한다.

fun createMember() = Member.of(
    1L,
    "tony",
    "tony",
    "tony@email.com",
    "tony",
    "test info"
)

fun createPost() = Post.of(
    title = "게시글 제목",
    content = "게시글 내용",
    member = createMember(),
    guild = createGuild()
)

fun createGuild() = Guild.of(
    "guild",
    "test guild"
)

fun createValidPostRequestDTO() = CreatePostRequestDTO(
    null,
    "게시글 제목",
    "게시글 내용",
    1L,
    1L
)

fun createNotValidPostRequestDTO() = CreatePostRequestDTO(
    null,
    "게시글 제목",
    "게시글 내용",
    100L,
    1L
)