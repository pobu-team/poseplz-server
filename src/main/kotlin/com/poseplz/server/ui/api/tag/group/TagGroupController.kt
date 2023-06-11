package com.poseplz.server.ui.api.tag.group

import com.poseplz.server.application.tag.group.toTagGroupResponse
import com.poseplz.server.domain.tag.group.TagGroupService
import com.poseplz.server.ui.api.ApiResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/tag-groups")
@RestController
class TagGroupController(
    private val tagGroupService: TagGroupService,
) {
    @GetMapping
    fun getTagGroups(): ApiResponse<List<TagGroupResponse>> {
        return ApiResponse.success(
            data = tagGroupService.findAll(Pageable.unpaged())
                .map { it.toTagGroupResponse() }
                .toList()
        )
    }

    @GetMapping("/{tagGroupId}")
    fun getTagGroup(
        @PathVariable tagGroupId: Long,
    ): ApiResponse<TagGroupResponse> {
        return ApiResponse.success(
            data = tagGroupService.getById(tagGroupId).toTagGroupResponse()
        )
    }
}
