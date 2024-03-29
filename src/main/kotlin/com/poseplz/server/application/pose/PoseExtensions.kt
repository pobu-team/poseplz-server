package com.poseplz.server.application.pose

import com.poseplz.server.application.file.toFileResponse
import com.poseplz.server.application.tag.toTagResponse
import com.poseplz.server.domain.pose.Pose
import com.poseplz.server.ui.api.pose.PoseDetailResponse
import com.poseplz.server.ui.api.pose.PoseSimpleResponse

fun Pose.toPoseDetailResponse(
    archived: Boolean,
): PoseDetailResponse {
    return PoseDetailResponse(
        poseId = this.poseId.toString(),
        thumbnailImageUrl = "/api/v1/files/${this.file.fileId}",
        imageUrl = "/api/v1/files/${this.file.fileId}",
        file = this.file.toFileResponse(),
        tags = this.poseTags.map { it.tag.toTagResponse() },
        peopleCount = this.peopleCount,
        archived = archived,
        sourceTitle = this.sourceTitle ?: "",
        sourceUrl = this.sourceUrl ?: "",
    )
}

fun Pose.toPoseSimpleResponse(
    archived: Boolean,
): PoseSimpleResponse {
    return PoseSimpleResponse(
        poseId = this.poseId.toString(),
        thumbnailImageUrl = "/api/v1/files/${this.file.fileId}",
        file = this.file.toFileResponse(),
        poseCount = this.peopleCount,
        archived = archived,
    )
}
