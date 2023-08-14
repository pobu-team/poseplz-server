package com.poseplz.server.domain.pose.archive

import com.poseplz.server.domain.pose.Pose
import com.poseplz.server.domain.pose.PoseQueryRequestVo
import org.springframework.data.domain.Slice

interface ArchivedPoseRepositoryCustom {
    fun findByMemberId(memberId: Long): List<Pose>
    fun findByMemberIdAndPoseId(memberId: Long, poseId: Long): Pose?
    fun findBy(memberId: Long, poseQueryRequestVo: PoseQueryRequestVo): Slice<Pose>
}
