package com.poseplz.server.domain.pose

import com.poseplz.server.domain.tag.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface PoseTagRepository : JpaRepository<PoseTag, Long> {
    fun findByTag(tag: Tag): List<PoseTag>
}
