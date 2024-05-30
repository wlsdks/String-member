package com.tony.string.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LikeController(

) {

    @GetMapping("/like")
    fun like(): String {
        return "like"
    }

}