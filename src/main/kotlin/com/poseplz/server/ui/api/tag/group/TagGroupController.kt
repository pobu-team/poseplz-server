package com.poseplz.server.ui.api.tag.group

import com.poseplz.server.application.tag.group.TagGroupApplicationService
import com.poseplz.server.application.tag.group.toTagGroupDetailResponse
import com.poseplz.server.domain.tag.group.TagGroupService
import com.poseplz.server.ui.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "태그 그룹", description = "태그 그룹 API")
@RequestMapping("/api/v1/tag-groups")
@RestController
class TagGroupController(
    private val tagGroupService: TagGroupService,
    private val tagGroupApplicationService: TagGroupApplicationService,
) {
    @Operation(summary = "태그 그룹 목록 조회", description = "태그 그룹 목록을 조회합니다.")
    @GetMapping
    fun getTagGroups(
        @RequestParam(required = false) peopleCount: Int?,
    ): ApiResponse<List<TagGroupResponse>> {
        return ApiResponse.success(
            data = tagGroupApplicationService.findByPeopleCount(peopleCount),
        )
    }

    @Operation(summary = "태그 그룹 단건 조회", description = "태그 그룹 1개를 조회합니다.")
    @GetMapping("/{tagGroupId}")
    fun getTagGroup(
        @PathVariable tagGroupId: Long,
    ): ApiResponse<TagGroupDetailResponse> {
        return ApiResponse.success(
            data = tagGroupService.getById(tagGroupId).toTagGroupDetailResponse(),
        )
    }
}
