package com.tony.string.repository

import com.tony.string.domain.Guild
import org.springframework.data.jpa.repository.JpaRepository

interface GuildRepository : JpaRepository<Guild, Long> {

}