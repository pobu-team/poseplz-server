package com.poseplz.server.domain.tag

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository : JpaRepository<Tag, Long> {
    fun findByType(tagType: TagType, pageable: Pageable): Page<Tag>
    fun findByTagIdIn(tagIds: List<Long>): List<Tag>
}
