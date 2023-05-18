package com.poseplz.server.application.tag

import com.poseplz.server.domain.tag.TagService
import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.ui.api.tag.TagResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class TagApplicationService(
    private val tagService: TagService,
) {
    fun findByTagType(tagType: TagType?, pageable: Pageable): Page<TagResponse> {
        val tags = if (tagType == null) {
            tagService.findAll(pageable)
        } else {
            tagService.findByTagType(tagType, pageable)
        }
        return tags.map { it.toTagResponse() }
    }
}
