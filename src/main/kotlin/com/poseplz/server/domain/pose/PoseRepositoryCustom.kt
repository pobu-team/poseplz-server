package com.poseplz.server.domain.pose

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PoseRepositoryCustom {
    fun findByTagIds(tagIds: Collection<Long>): List<Pose>
    fun findByTagGroupIdsAndPeopleCount(tagGroupIds: Collection<Long>, peopleCount: Int): List<Pose>
    fun findOrderByArchive(pageable: Pageable): Page<Pose>
}
