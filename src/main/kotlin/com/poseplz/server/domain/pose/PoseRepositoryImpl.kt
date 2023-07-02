package com.poseplz.server.domain.pose

import com.poseplz.server.domain.tag.QTag
import com.poseplz.server.domain.tag.group.QTagGroupTag
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PoseRepositoryImpl : PoseRepositoryCustom, QuerydslRepositorySupport(Pose::class.java) {
    private val pose = QPose.pose
    private val poseTag = QPoseTag.poseTag
    private val tagGroupTag = QTagGroupTag.tagGroupTag
    private val tag = QTag.tag

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

    override fun findByTagGroupIdsAndPeopleCount(
        tagGroupIds: Collection<Long>,
        peopleCount: Int,
    ): List<Pose> {
        val postIds = from(tagGroupTag)
            .leftJoin(tagGroupTag.tag, tag)
            .leftJoin(tag.poseTags, poseTag)
            .where(tagGroupTag.tagGroup.tagGroupId.`in`(tagGroupIds)
                .and(pose.peopleCount.eq(peopleCount))
            )
            .groupBy(poseTag.pose.poseId)
            .having(poseTag.pose.poseId.count().goe(tagGroupIds.size.toLong()))
            .select(poseTag.pose.poseId)
            .fetch()
        return from(pose)
            .where(pose.poseId.`in`(postIds))
            .fetch()
    }
}
