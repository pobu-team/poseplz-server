package com.poseplz.server.application.pose

import com.poseplz.server.application.tag.toTagResponse
import com.poseplz.server.domain.pose.Pose
import com.poseplz.server.ui.api.pose.PoseDetailResponse
import com.poseplz.server.ui.api.pose.PoseSimpleResponse

fun Pose.toPoseDetailResponse(): PoseDetailResponse {
    return PoseDetailResponse(
        poseId = this.poseId.toString(),
        thumbnailImageUrl = "/api/v1/files/${this.file.fileId}",
        imageUrl = "/api/v1/files/${this.file.fileId}",
        tags = this.poseTags.map { it.tag.toTagResponse() },
        peopleCount = this.peopleCount,
    )
}

fun Pose.toPoseSimpleResponse(): PoseSimpleResponse {
    return PoseSimpleResponse(
        poseId = this.poseId.toString(),
        thumbnailImageUrl = "/api/v1/files/${this.file.fileId}",
        poseCount = this.peopleCount,
    )
}
