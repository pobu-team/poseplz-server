package com.poseplz.server.ui.admin.tag

import com.poseplz.server.domain.tag.TagCreateVo
import com.poseplz.server.domain.tag.TagService
import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.domain.tag.TagUpdateVo
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
    /**
     * 태그 목록 조회
     */
    @GetMapping
    fun list(
        pageable: Pageable,
        model: Model,
    ): String {
        model.addAttribute("tags", tagService.findAll(pageable).content)
        return "tag/list"
    }

    /**
     * 태그 상세 조회
     */
    @GetMapping("/{tagId}")
    fun detail(
        @PathVariable tagId: Long,
        model: Model,
    ): String {
        model.addAttribute("tag", tagService.getById(tagId))
        return "tag/detail"
    }

    /**
     * 태그 추가
     */
    @GetMapping("/add")
    fun add(
        model: Model,
    ): String {
        model.addAttribute("tagTypes", TagType.values().toList())
        return "tag/add"
    }

    /**
     * 태그 추가 처리
     */
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

    /**
     * 태그 수정
     */
    @GetMapping("/{tagId}/edit")
    fun edit(
        @PathVariable tagId: Long,
        model: Model,
    ): String {
        model.addAttribute("tag", tagService.getById(tagId))
        model.addAttribute("tagTypes", TagType.values().toList())
        return "tag/edit"
    }

    /**
     * 태그 수정 처리
     */
    @PostMapping("/{tagId}/edit")
    fun editSubmit(
        @PathVariable tagId: Long,
        @ModelAttribute tagEditRequest: TagEditRequest
    ): String {
        val tag = tagService.update(
            tagId = tagId,
            tagUpdateVo = TagUpdateVo(
                tagType = tagEditRequest.tagType!!,
                name = tagEditRequest.name!!,
                selectorName = tagEditRequest.selectorName!!,
                selectorDisplayOrder = tagEditRequest.selectorDisplayOrder ?: 0,
                emojiImageUrl = tagEditRequest.emojiImageUrl,
                emojiText = tagEditRequest.emojiText,
                description = tagEditRequest.description,
            )
        )
        return "redirect:/tag/${tag.tagId}"
    }
}
