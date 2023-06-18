package com.poseplz.server.application.tag.group

import com.poseplz.server.domain.tag.group.TagGroupService
import com.poseplz.server.ui.api.tag.group.TagGroupResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class TagGroupApplicationService(
    private val tagGroupService: TagGroupService,
) {
    fun findByPeopleCount(peopleCount: Int?): List<TagGroupResponse> {
        return if (peopleCount == null) {
            tagGroupService.findAll(Pageable.unpaged()).content
        } else {
            tagGroupService.findByPeopleCount(peopleCount)
        }.map { it.toTagGroupResponse() }
    }
}
