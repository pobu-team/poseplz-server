package com.poseplz.server.domain.tag.group

import com.poseplz.server.domain.tag.TagService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface TagGroupTagService {
    fun create(tagGroupId: Long, tagId: Long): TagGroupTag
    fun update(tagGroupId: Long, tagIds: List<Long>): List<TagGroupTag>
    fun delete(tagGroupId: Long, tagId: Long)
}

@Service
@Transactional(readOnly = true)
class TagGroupTagServiceImpl(
    private val tagGroupService: TagGroupService,
    private val tagService: TagService,
    private val tagGroupTagRepository: TagGroupTagRepository,
) : TagGroupTagService {
    @Transactional
    override fun create(
        tagGroupId: Long,
        tagId: Long,
    ): TagGroupTag {
        val tagGroup = tagGroupService.getById(tagGroupId)
        val tag = tagService.getById(tagId)
        return tagGroupTagRepository.save(
            TagGroupTag.of(tagGroup, tag)
        )
    }

    @Transactional
    override fun update(
        tagGroupId: Long,
        tagIds: List<Long>,
    ): List<TagGroupTag> {
        val tagGroup = tagGroupService.getById(tagGroupId)
        val tags = tagService.findByTagIds(tagIds)
        tagGroupTagRepository.deleteByTagGroup_tagGroupId(tagGroupId)
        return tagGroupTagRepository.saveAll(
            tags.map { TagGroupTag.of(tagGroup, it) }
        )
    }

    @Transactional
    override fun delete(
        tagGroupId: Long,
        tagId: Long,
    ) {
        tagGroupTagRepository.findByTagGroup_tagGroupIdAndTag_tagId(tagGroupId, tagId)
            ?.let { tagGroupTagRepository.delete(it) }
    }
}
