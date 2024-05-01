package com.tony.string

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

@SpringBootApplication
class StringApplication

fun main(args: Array<String>) {
    runApplication<StringApplication>(*args)
}
