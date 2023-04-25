package com.poseplz.server.ui.admin.tag

import com.poseplz.server.domain.tag.TagCreateVo
import com.poseplz.server.domain.tag.TagService
import com.poseplz.server.domain.tag.TagType
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/tag")
@Controller
class AdminTagController(
    private val tagService: TagService,
) {
    @GetMapping
    fun index(
        pageable: Pageable,
        model: Model,
    ): String {
        val tagPage = tagService.findAll(pageable)
        model.addAttribute("tags", tagPage.content)
        return "tag/index"
    }

    @GetMapping("{tagId}")
    fun index(
        @PathVariable tagId: Long,
        model: Model,
    ): String {
        val tag = tagService.getById(tagId)
        model.addAttribute("tag", tag)
        return "tag/detail"
    }

    @GetMapping("/add")
    fun add(
        model: Model,
    ): String {
        model.addAttribute("tagTypes", TagType.values().map { it.name }.toList())
        return "tag/add"
    }

    @PostMapping("/add")
    fun addSubmit(
        @ModelAttribute @Valid tagAddRequest: TagAddRequest,
    ): String {
        val tag = tagService.create(
            tagCreateVo = TagCreateVo(
                tagType = tagAddRequest.tagType!!,
                name = tagAddRequest.name!!,
                selectorName = tagAddRequest.selectorName!!,
                selectorDisplayOrder = tagAddRequest.selectorDisplayOrder ?: 0,
                emojiImageUrl = tagAddRequest.emojiImageUrl,
                emojiText = tagAddRequest.emojiText,
                description = tagAddRequest.description,
            ),
        )
        return "redirect:/tag/${tag.tagId}"
    }
}
