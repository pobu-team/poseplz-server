package com.poseplz.server.domain.pose.archive

import com.poseplz.server.domain.pose.Pose
import com.poseplz.server.domain.pose.PoseQueryRequestVo
import com.poseplz.server.domain.pose.QPose
import com.poseplz.server.domain.pose.QPoseTag
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class ArchivedPoseRepositoryImpl : ArchivedPoseRepositoryCustom, QuerydslRepositorySupport(ArchivedPose::class.java) {
    private val pose = QPose.pose
    private val archivedPose = QArchivedPose.archivedPose
    private val poseTag = QPoseTag.poseTag

    override fun findByMemberId(memberId: Long): List<Pose> {
        return from(pose)
            .innerJoin(archivedPose).on(pose.poseId.eq(archivedPose.pose.poseId))
            .where(archivedPose.member.memberId.eq(memberId))
            .orderBy(archivedPose.createdAt.desc())
            .select(pose)
            .fetch()
    }

    override fun findByMemberIdAndPoseId(memberId: Long, poseId: Long): Pose? {
        return from(pose)
            .innerJoin(archivedPose).on(pose.poseId.eq(archivedPose.pose.poseId))
            .where(
                archivedPose.member.memberId.eq(memberId),
                archivedPose.pose.poseId.eq(poseId)
            )
            .select(pose)
            .fetchOne()
    }

    override fun findBy(memberId: Long, poseQueryRequestVo: PoseQueryRequestVo): Slice<Pose> {
        val query = if (poseQueryRequestVo.tagIds.isNotEmpty()) {
            from(pose)
                .innerJoin(archivedPose).on(pose.poseId.eq(archivedPose.pose.poseId))
                .innerJoin(pose.poseTags, poseTag)
                .where(
                    archivedPose.member.memberId.eq(memberId),
                    poseTag.tag.tagId.`in`(poseQueryRequestVo.tagIds),
                )
        } else {
            from(pose)
                .innerJoin(archivedPose).on(pose.poseId.eq(archivedPose.pose.poseId))
                .where(
                    archivedPose.member.memberId.eq(memberId),
                )
        }
        val fetchResult = query.orderBy(archivedPose.createdAt.desc())
            .select(pose)
            .limit(poseQueryRequestVo.pageable.pageSize.toLong() + 1)
            .offset(poseQueryRequestVo.pageable.offset)
            .fetch()

        val hasNext = fetchResult.size > poseQueryRequestVo.pageable.pageSize.toLong()
        return SliceImpl(
            if (hasNext) fetchResult.subList(0, poseQueryRequestVo.pageable.pageSize) else fetchResult,
            poseQueryRequestVo.pageable,
            hasNext,
        )
    }
}
