package com.poseplz.server.application.pose

import com.poseplz.server.domain.pose.PoseQueryRequestVo
import com.poseplz.server.domain.pose.archive.ArchivedPoseService
import com.poseplz.server.ui.api.pose.PoseSimpleResponse
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Component

@Component
class PoseArchiveApplicationService(
    private val archivedPoseService: ArchivedPoseService,
) {
    fun getArchivedPoses(
        memberId: Long,
        poseQueryRequestVo: PoseQueryRequestVo,
    ): Slice<PoseSimpleResponse> {
        return archivedPoseService.getArchivedPoses(memberId, poseQueryRequestVo)
            .map { it.toPoseSimpleResponse(true) }
    }

    fun archivePose(memberId: Long, poseId: Long): PoseSimpleResponse {
        return archivedPoseService.archivePose(memberId, poseId).toPoseSimpleResponse(true)
    }

    fun unarchivePose(memberId: Long, poseId: Long) {
        archivedPoseService.unarchivePose(memberId, poseId)
    }

    fun unarchiveAllPoses(memberId: Long) {
        archivedPoseService.unarchiveAllPoses(memberId)
    }
}
