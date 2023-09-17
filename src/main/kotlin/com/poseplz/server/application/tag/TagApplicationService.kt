package com.poseplz.server.application.tag

import com.poseplz.server.domain.pose.PoseService
import com.poseplz.server.domain.tag.TagService
import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.ui.admin.tag.TagCountResponse
import com.poseplz.server.ui.api.tag.TagDetailResponse
import com.poseplz.server.ui.api.tag.TagResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class TagApplicationService(
    private val tagService: TagService,
    private val poseService: PoseService,
) {
    fun findByTagType(tagType: TagType?, pageable: Pageable): Page<TagResponse> {
        val tags = if (tagType == null) {
            tagService.findAll(pageable)
        } else {
            tagService.findByTagType(tagType, pageable)
        }
        return tags.map { it.toTagResponse() }
    }

    fun findWithCount(tagType: TagType?, pageable: Pageable): Page<TagCountResponse> {
        val tags = if (tagType == null) {
            tagService.findAll(pageable)
        } else {
            tagService.findByTagType(tagType, pageable)
        }
        return tags.map {
            val count = poseService.countByTagId(it.tagId)
            TagCountResponse.of(it, count.toInt())
        }
    }

    fun getTagByTagId(tagId: Long): TagDetailResponse {
        val tag = tagService.getById(tagId)
        return tag.toTagDetailResponse()
    }

    /**
     * tagId 로 나머지 tagIds 를 합침
     */
    fun merge(tagId: Long, tagIds: List<Long>) {
        tagIds.forEach {
            tagService.merge(it, tagId)
        }
    }
}
