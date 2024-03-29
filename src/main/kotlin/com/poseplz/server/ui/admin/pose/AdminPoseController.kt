package com.poseplz.server.ui.admin.pose

import com.poseplz.server.application.file.FileUploadVo
import com.poseplz.server.application.pose.PoseApplicationService
import com.poseplz.server.application.tag.TagApplicationService
import com.poseplz.server.domain.pose.PoseService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/pose")
@Controller
class AdminPoseController(
    private val poseService: PoseService,
    private val poseApplicationService: PoseApplicationService,
    private val tagApplicationService: TagApplicationService,
) {
    @GetMapping
    fun list(
        @PageableDefault(size = 10000, sort = ["poseId"], direction = Sort.Direction.DESC) pageable: Pageable,
        model: Model,
    ): String {
        model.addAttribute("poses", poseService.findAll(pageable))
        model.addAttribute("tags", tagApplicationService.findWithCount(null, Pageable.unpaged()))
        return "pose/list"
    }

    @PostMapping
    fun search(
        @ModelAttribute poseSearchRequest: PostSearchRequest,
        @PageableDefault(size = 10000, sort = ["poseId"], direction = Sort.Direction.DESC) pageable: Pageable,
        model: Model,
    ): String {
        model.addAttribute("poses", poseService.findBy(
            tagIds = poseSearchRequest.tagIds.map { it.toLong() },
            pageable = pageable,
        ))
        model.addAttribute("tags", tagApplicationService.findWithCount(null, Pageable.unpaged()))
        return "pose/list"
    }

    @GetMapping("/{poseId}")
    fun detail(
        @PathVariable poseId: Long,
        model: Model,
    ): String {
        model.addAttribute("pose", poseService.getById(poseId))
        return "pose/detail"
    }

    @GetMapping("/add")
    fun add(
        model: Model,
    ): String {
        model.addAttribute("tags", tagApplicationService.findWithCount(null, Pageable.unpaged()))
        return "pose/add"
    }

    @PostMapping("/add", consumes = ["multipart/form-data"])
    fun addSubmit(
        @ModelAttribute poseAddRequest: PoseAddRequest,
    ): String {
        val poseDetailResponse = poseApplicationService.create(
            inputStream = poseAddRequest.file.inputStream,
            fileUploadVo = FileUploadVo(
                name = poseAddRequest.file.originalFilename!!,
                contentType = poseAddRequest.file.contentType!!,
                size = poseAddRequest.file.size,
            ),
            tagIds = poseAddRequest.tagIds.map { it.toLong() },
            peopleCount = poseAddRequest.peopleCount,
            sourceTitle = poseAddRequest.sourceTitle,
            sourceUrl = poseAddRequest.sourceURL,
        )
        return "redirect:/pose"
    }

    @GetMapping("/{poseId}/edit")
    fun edit(
        @PathVariable poseId: Long,
        model: Model,
    ): String {
        val pose = poseService.getById(poseId)
        model.addAttribute("pose", pose)
        model.addAttribute("selectedTagIds", pose.poseTags.map { it.tag.tagId }.toSet())
        model.addAttribute("tags", tagApplicationService.findWithCount(null, Pageable.unpaged()))
        return "pose/edit"
    }

    @PostMapping("/{poseId}/edit")
    fun editSubmit(
        @PathVariable poseId: Long,
        @ModelAttribute poseEditRequest: PoseEditRequest,
        model: Model,
    ): String {
        val pose = poseApplicationService.update(
            poseId = poseId,
            inputStream = poseEditRequest.file.inputStream,
            fileUploadVo = FileUploadVo(
                name = poseEditRequest.file.originalFilename!!,
                contentType = poseEditRequest.file.contentType!!,
                size = poseEditRequest.file.size,
            ),
            tagIds = poseEditRequest.tagIds.map { it.toLong() },
            peopleCount = poseEditRequest.peopleCount,
            sourceTitle = poseEditRequest.sourceTitle,
            sourceUrl = poseEditRequest.sourceUrl
        )
        model.addAttribute("pose", pose)
        return "redirect:/pose/${poseId}"
    }

    @PostMapping("/{postId}/delete")
    fun deleteSubmit(
        @PathVariable postId: Long,
    ): String {
        poseService.deleteByAdmin(postId)
        return "redirect:/pose"
    }

}
