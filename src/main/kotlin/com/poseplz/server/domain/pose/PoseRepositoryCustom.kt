package com.poseplz.server.domain.pose

interface PoseRepositoryCustom {
    fun findByTagIds(tagIds: Collection<Long>): List<Pose>
    fun findByTagGroupIds(tagGroupIds: Collection<Long>): List<Pose>
}
