package com.poseplz.server.domain.pose

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PoseRepository : JpaRepository<Pose, Long>, PoseRepositoryCustom {
    @Suppress("FunctionName")
    fun findByPoseTags_Tag_TagIdIn(tagIds: Collection<Long>, pageable: Pageable): Page<Pose>
}
