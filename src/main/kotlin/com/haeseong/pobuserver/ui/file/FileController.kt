package com.haeseong.pobuserver.ui.file

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/files")
class FileController {
    @GetMapping("/{fileId}")
    fun getFile(
        @PathVariable fileId: Long,
        response: HttpServletResponse,
    ) {
        response.setHeader("Content-Disposition", "attachment; filename=\"test.txt\"")
        response.setHeader("Content-Type", "text/plain")
        response.outputStream.write("test".toByteArray())
    }
}
