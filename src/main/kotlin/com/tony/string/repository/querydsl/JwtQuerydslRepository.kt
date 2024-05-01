package com.tony.string.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class JwtQuerydslRepository(
    private val jpaQueryFactory: JPAQueryFactory,
)
