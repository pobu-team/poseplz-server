package com.poseplz.server.ui.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/file")
@Controller
class AdminFileController {
    @GetMapping
    fun index() = "file/index"
}
