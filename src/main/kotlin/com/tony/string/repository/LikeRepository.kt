package com.tony.string.repository

import com.tony.string.domain.Likes
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Likes, Long>{
}