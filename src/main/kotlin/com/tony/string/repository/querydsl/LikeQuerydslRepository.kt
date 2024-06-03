package com.tony.string.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class LikeQuerydslRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {

}