package com.poseplz.server.domain.pose.archive

import org.springframework.data.jpa.repository.JpaRepository


interface ArchivedPoseRepository : JpaRepository<ArchivedPose, Long>, ArchivedPoseRepositoryCustom {
    @Suppress("FunctionName")
    fun findByPose_poseId(poseId: Long): List<ArchivedPose>

    @Suppress("FunctionName")
    fun findByMember_memberIdAndPose_poseId(memberId: Long, poseId: Long): ArchivedPose?

    @Suppress("FunctionName")
    fun deleteByMember_memberIdAndPose_poseId(memberId: Long, poseId: Long)

    @Suppress("FunctionName")
    fun deleteByMember_memberId(memberId: Long)
}
