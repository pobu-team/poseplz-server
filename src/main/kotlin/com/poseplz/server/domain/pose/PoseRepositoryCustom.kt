package com.poseplz.server.domain.pose

interface PoseRepositoryCustom {
    fun findByTagIds(tagIds: Collection<Long>): List<Pose>
    fun findByTagGroupIdsAndPeopleCount(tagGroupIds: Collection<Long>, peopleCount: Int): List<Pose>
}
