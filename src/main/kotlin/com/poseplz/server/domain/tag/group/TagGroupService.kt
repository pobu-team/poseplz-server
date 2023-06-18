package com.poseplz.server.domain.tag.group

import com.poseplz.server.domain.tag.TagService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface TagGroupService {
    fun create(tagGroupCreateVo: TagGroupCreateVo): TagGroup
    fun update(tagGroupId: Long, tagGroupUpdateVo: TagGroupUpdateVo): TagGroup
    fun delete(tagGroupId: Long)
    fun findAll(pageable: Pageable): Page<TagGroup>
    fun findByPeopleCount(peopleCount: Int): List<TagGroup>
    fun findById(tagGroupId: Long): TagGroup?
    fun getById(tagGroupId: Long): TagGroup
}

@Service
@Transactional(readOnly = true)
class TagGroupServiceImpl(
    private val tagGroupRepository: TagGroupRepository,
    private val tagService: TagService,
) : TagGroupService {
    @Transactional
    override fun create(tagGroupCreateVo: TagGroupCreateVo): TagGroup {
        val tags = tagService.findByTagIds(tagGroupCreateVo.tagIds)
        return tagGroupRepository.save(
            TagGroup.of(tagGroupCreateVo, tags),
        )
    }

    @Transactional
    override fun update(tagGroupId: Long, tagGroupUpdateVo: TagGroupUpdateVo): TagGroup {
        val tagGroup = (tagGroupRepository.findByIdOrNull(tagGroupId)
            ?: throw TagGroupNotFoundException())
        val tagGroupTags = tagService.findByTagIds(tagGroupUpdateVo.tagIds)
            .map { TagGroupTag.of(tagGroup, it) }
        return tagGroup.apply {
            update(tagGroupUpdateVo, tagGroupTags)
        }
    }

    @Transactional
    override fun delete(tagGroupId: Long) {
        tagGroupRepository.findByIdOrNull(tagGroupId)
            ?.let { tagGroupRepository.delete(it) }
    }

    override fun findAll(pageable: Pageable): Page<TagGroup> {
        return tagGroupRepository.findAll(pageable)
    }

    override fun findByPeopleCount(peopleCount: Int): List<TagGroup> {
        return tagGroupRepository.findByPeopleCountsContains(peopleCount)
    }

    override fun findById(tagGroupId: Long): TagGroup? {
        return tagGroupRepository.findByIdOrNull(tagGroupId)
    }

    override fun getById(tagGroupId: Long): TagGroup {
        return tagGroupRepository.findByIdOrNull(tagGroupId)
            ?: throw TagGroupNotFoundException()
    }

}
