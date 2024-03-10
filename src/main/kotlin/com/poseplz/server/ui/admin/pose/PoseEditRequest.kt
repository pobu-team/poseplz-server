package com.poseplz.server.ui.admin.pose

import org.springframework.web.multipart.MultipartFile

data class PoseEditRequest(
    val file: MultipartFile,
    val tagIds: List<String> = emptyList(),
    val peopleCount: Int,
    val sourceTitle: String?,
    val sourceUrl: String?
)
