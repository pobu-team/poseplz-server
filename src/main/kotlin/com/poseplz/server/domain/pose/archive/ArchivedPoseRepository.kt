package com.poseplz.server.domain.pose.archive

import org.springframework.data.jpa.repository.JpaRepository


interface ArchivedPoseRepository : JpaRepository<ArchivedPose, Long>, ArchivedPoseRepositoryCustom {
    @Suppress("FunctionName")
    fun deleteByMember_memberIdAndPose_poseId(memberId: Long, poseId: Long)

    @Suppress("FunctionName")
    fun deleteByMember_memberId(memberId: Long)
}
