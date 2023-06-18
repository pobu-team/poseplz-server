package com.poseplz.server.ui.api.tag.group

import com.poseplz.server.application.tag.group.TagGroupApplicationService
import com.poseplz.server.application.tag.group.toTagGroupDetailResponse
import com.poseplz.server.application.tag.group.toTagGroupResponse
import com.poseplz.server.domain.tag.group.TagGroupService
import com.poseplz.server.ui.api.ApiResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/tag-groups")
@RestController
class TagGroupController(
    private val tagGroupService: TagGroupService,
    private val tagGroupApplicationService: TagGroupApplicationService,
) {
    @GetMapping
    fun getTagGroups(
        @RequestParam(required = false) peopleCount: Int?,
    ): ApiResponse<List<TagGroupResponse>> {
        return ApiResponse.success(
            data = tagGroupApplicationService.findByPeopleCount(peopleCount),
        )
    }

    @GetMapping("/{tagGroupId}")
    fun getTagGroup(
        @PathVariable tagGroupId: Long,
    ): ApiResponse<TagGroupDetailResponse> {
        return ApiResponse.success(
            data = tagGroupService.getById(tagGroupId).toTagGroupDetailResponse(),
        )
    }
}
