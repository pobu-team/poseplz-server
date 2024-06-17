package com.poseplz.server.domain.pose

import com.poseplz.server.domain.pose.archive.QArchivedPose
import com.poseplz.server.domain.tag.QTag
import com.poseplz.server.domain.tag.group.QTagGroupTag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class PoseRepositoryImpl : PoseRepositoryCustom, QuerydslRepositorySupport(Pose::class.java) {
    private val pose = QPose.pose
    private val poseTag = QPoseTag.poseTag
    private val tagGroupTag = QTagGroupTag.tagGroupTag
    private val tag = QTag.tag
    private val archivedPose = QArchivedPose.archivedPose

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
            .where(
                tagGroupTag.tagGroup.tagGroupId.`in`(tagGroupIds)
                    .and(pose.peopleCount.eq(peopleCount))
            )
            .groupBy(poseTag.pose.poseId)
            .having(poseTag.pose.poseId.count().goe(tagGroupIds.size.toLong()))
            .select(poseTag.pose.poseId)
            .fetch()
        return from(pose)
            .where(pose.poseId.`in`(postIds))
            .orderBy(pose.createdAt.desc())
            .fetch()
    }

    override fun findOrderByArchive(pageable: Pageable): Page<Pose> {
        val query = from(pose)
            .leftJoin(archivedPose).on(archivedPose.pose.eq(pose))
            .orderBy(archivedPose.count().desc(), pose.createdAt.desc())
            .groupBy(pose.poseId)
        val contents = querydsl!!.applyPagination(pageable, query)
            .fetch()
        return PageableExecutionUtils.getPage(contents, pageable, from(pose)::fetchCount);
    }
}
