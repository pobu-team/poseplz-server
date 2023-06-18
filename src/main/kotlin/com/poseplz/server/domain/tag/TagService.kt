package com.poseplz.server.domain.tag

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface TagService {
    fun create(tagCreateVo: TagCreateVo): Tag
    fun update(tagId: Long, tagUpdateVo: TagUpdateVo): Tag
    fun findAll(pageable: Pageable): Page<Tag>
    fun findByTagType(tagType: TagType, pageable: Pageable): Page<Tag>
    fun findByTagIds(tagIds: List<Long>): List<Tag>
    fun getById(tagId: Long): Tag
}

@Service
@Transactional(readOnly = true)
class TagServiceImpl(
    private val tagRepository: TagRepository,
) : TagService {
    @Transactional
    override fun create(tagCreateVo: TagCreateVo): Tag {
        return tagRepository.save(Tag.from(tagCreateVo))
    }

    @Transactional
    override fun update(tagId: Long, tagUpdateVo: TagUpdateVo): Tag {
        val tag = tagRepository.findByIdOrNull(tagId)
            ?: throw TagNotFoundException("태그가 존재하지 않습니다. tagId: $tagId")
        tag.update(tagUpdateVo)
        return tag
    }

    override fun findAll(pageable: Pageable): Page<Tag> {
        return tagRepository.findAll(pageable)
    }

    override fun findByTagType(tagType: TagType, pageable: Pageable): Page<Tag> {
        return tagRepository.findByType(tagType, pageable)
    }

    override fun findByTagIds(tagIds: List<Long>): List<Tag> {
        return tagRepository.findByTagIdIn(tagIds)
    }

    override fun getById(tagId: Long): Tag {
        return tagRepository.findByIdOrNull(tagId)
            ?: throw TagNotFoundException("태그가 존재하지 않습니다. tagId: $tagId")
    }
}
