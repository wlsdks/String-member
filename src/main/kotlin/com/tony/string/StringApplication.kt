package com.tony.string

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class StringApplication

fun main(args: Array<String>) {
	runApplication<StringApplication>(*args)
}
