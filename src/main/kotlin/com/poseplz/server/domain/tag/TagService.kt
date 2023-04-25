package com.poseplz.server.domain.tag

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface TagService {
    fun create(tagCreateVo: TagCreateVo): Tag
    fun findAll(pageable: Pageable): Page<Tag>
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

    override fun findAll(pageable: Pageable): Page<Tag> {
        return tagRepository.findAll(pageable)
    }

    override fun getById(tagId: Long): Tag {
        return tagRepository.findByIdOrNull(tagId)
            ?: throw TagNotFoundException("태그가 존재하지 않습니다. tagId: $tagId")
    }
}
