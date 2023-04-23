package com.haeseong.pobuserver.ui.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/dashboard")
@Controller
class AdminDashboardController {
    @GetMapping
    fun index() = "dashboard/index"
}
