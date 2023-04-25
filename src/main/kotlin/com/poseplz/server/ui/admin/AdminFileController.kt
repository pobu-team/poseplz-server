package com.poseplz.server.ui.admin

import com.poseplz.server.domain.file.FileNotFoundException
import com.poseplz.server.domain.file.FileService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/file")
@Controller
class AdminFileController(
    private val fileService: FileService,
) {
    @GetMapping
    fun index(
        pageable: Pageable,
        model: Model
    ): String {
        val filePage = fileService.findAll(pageable)
        model.addAttribute("files", filePage.content)
        return "file/index"
    }

    @GetMapping("{fileId}")
    fun detail(
        @PathVariable fileId: Long,
        model: Model
    ): String {
        val file = fileService.findById(fileId) ?: throw FileNotFoundException("파일을 찾을 수 없습니다. fileId: $fileId")
        model.addAttribute("file", file)
        return "file/detail"
    }
}
