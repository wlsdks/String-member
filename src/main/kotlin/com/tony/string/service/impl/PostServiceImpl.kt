package com.tony.string.service.impl

import com.tony.string.controller.request.CreatePostRequestDTO
import com.tony.string.domain.Post
import com.tony.string.exception.CustomException
import com.tony.string.exception.ErrorCode
import com.tony.string.logger
import com.tony.string.repository.MemberRepository
import com.tony.string.repository.PostRepository
import com.tony.string.service.PostService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) : PostService {

    val log = logger()

    // 게시글 작성
    @Transactional
    override fun createPost(createPostRequestDTO: CreatePostRequestDTO): Post {
        /**
         * 여기에서 takeIf { it }가 사용되는데, it은 existsById에서 반환된 Boolean 값 (true 또는 false)을 나타낸다.
         * takeIf는 주어진 조건이 참일 경우에만 호출 객체(여기서는 existsById의 결과)를 반환하고, 거짓일 경우 null을 반환한다.
         * 이 경우 조건은 { it } 즉, existsById의 결과가 true일 때 참이 되므로, 멤버가 존재하면 true를 그대로 반환하고, 멤버가 존재하지 않으면 null을 반환하게 된다.
         * 엘비스 연산자 ?:는 왼쪽 피연산자가 null이 아닐 경우 그 값을 그대로 사용하고, null일 경우 오른쪽 피연산자를 실행하는데 사용된다.
         * 즉, 멤버가 존재하지 않아 takeIf에서 null이 반환되면 오른쪽에 있는 throw CustomException(ErrorCode.MEMBER_NOT_FOUND)가 실행되어 예외가 발생한다.
         */
        memberRepository.existsById(createPostRequestDTO.memberId).takeIf { it }
            ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

        return postRepository.save(Post.fromDto(createPostRequestDTO))
    }

    // 게시글 단건 상세조회


    // 게시글 목록 조회


    // 게시글 수정


    // 게시글 삭제

}
