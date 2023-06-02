package com.poseplz.server.domain.pose

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PoseRepositoryImpl : PoseRepositoryCustom, QuerydslRepositorySupport(Pose::class.java) {
    private val pose = QPose.pose
    private val poseTag = QPoseTag.poseTag

    override fun findByTagIds(tagIds: Collection<Long>): List<Pose> {
        val postIds = from(poseTag)
            .where(poseTag.tag.tagId.`in`(tagIds))
            .groupBy(poseTag.pose.poseId)
            .having(poseTag.pose.poseId.count().eq(tagIds.size.toLong()))
            .select(poseTag.pose.poseId)
            .fetch()
        return from(pose)
            .where(pose.poseId.`in`(postIds))
            .fetch()
    }
}
