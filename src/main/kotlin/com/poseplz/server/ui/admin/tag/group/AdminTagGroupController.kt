package com.poseplz.server.ui.admin.tag.group

import com.poseplz.server.domain.tag.group.TagGroupCreateVo
import com.poseplz.server.domain.tag.group.TagGroupService
import com.poseplz.server.domain.tag.group.TagGroupUpdateVo
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/tag-group")
@Controller
class AdminTagGroupController(
    private val tagGroupService: TagGroupService,
) {
    @GetMapping
    fun list(
        @PageableDefault pageable: Pageable,
        model: Model,
    ): String {
        model.addAttribute("tagGroups", tagGroupService.findAll(pageable).content)
        return "tag/group/list"
    }

    @GetMapping("/{tagGroupId}")
    fun detail(
        @PathVariable tagGroupId: Long,
        model: Model,
    ): String {
        model.addAttribute("tagGroup", tagGroupService.findById(tagGroupId))
        return "tag/group/detail"
    }

    @GetMapping("/add")
    fun add(
        model: Model,
    ): String {
        return "tag/group/add"
    }

    @PostMapping("/add")
    fun addSubmit(
        @ModelAttribute tagGroupCreateVo: TagGroupCreateVo,
        model: Model,
    ): String {
        val tagGroup = tagGroupService.create(tagGroupCreateVo)
        model.addAttribute("tagGroup", tagGroup)
        return "redirect:/tag-group"
    }

    @GetMapping("/{tagGroupId}/edit")
    fun edit(
        @PathVariable tagGroupId: Long,
        model: Model,
    ): String {
        model.addAttribute("tagGroup", tagGroupService.findById(tagGroupId))
        return "tag/group/edit"
    }

    @PostMapping("/{tagGroupId}/edit")
    fun editSubmit(
        @PathVariable tagGroupId: Long,
        @ModelAttribute tagGroupUpdateVo: TagGroupUpdateVo,
        model: Model,
    ): String {
        val tagGroup = tagGroupService.update(tagGroupId, tagGroupUpdateVo)
        return "redirect:/tag-group/${tagGroup.tagGroupId}"
    }
}
