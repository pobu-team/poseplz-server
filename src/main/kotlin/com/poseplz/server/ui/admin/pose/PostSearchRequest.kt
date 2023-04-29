package com.poseplz.server.ui.admin.pose

data class PostSearchRequest(
    val tagIds: List<String> = emptyList(),
)
