package com.poseplz.server.ui.admin.photobooth

data class PhotoBoothEditRequest(
    val name: String?,
    val brandId: Long?,
    val description: String?,
    val logoImageUrl: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
)
