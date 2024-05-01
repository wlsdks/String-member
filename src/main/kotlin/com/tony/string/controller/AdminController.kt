package com.tony.string.controller

import com.tony.string.config.security.role.AdminAuthorize
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "관리자만 사용할 수 있는 API")
@RestController
@AdminAuthorize
@RequestMapping("/admin")
class AdminController
