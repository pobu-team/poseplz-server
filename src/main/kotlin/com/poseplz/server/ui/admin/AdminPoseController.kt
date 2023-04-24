package com.poseplz.server.ui.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/pose")
@Controller
class AdminPoseController {
    @GetMapping
    fun index() = "pose/index"
}
