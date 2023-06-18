package com.poseplz.server.ui.api.tag

import com.poseplz.server.application.tag.TagApplicationService
import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.ui.api.ApiResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tags")
class TagController (
    private val tagApplicationService: TagApplicationService,
) {
    @GetMapping
    fun getTags(
        @RequestParam(required = false) tagType: TagType?,
    ): ApiResponse<List<TagResponse>> {
        return ApiResponse.success(
            data = tagApplicationService.findByTagType(tagType, Pageable.unpaged()).content,
        )
    }

    @GetMapping("/{tagId}")
    fun getTag(
        @PathVariable tagId: Long,
    ): ApiResponse<TagDetailResponse> {
        return ApiResponse.success(
            data = tagApplicationService.getTagByTagId(tagId),
        )
    }
}
