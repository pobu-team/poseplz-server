package com.poseplz.server.ui.admin.photobooth

data class PhotoBoothSearchRequest(
    val brandIds: List<Long> = emptyList(),
)
