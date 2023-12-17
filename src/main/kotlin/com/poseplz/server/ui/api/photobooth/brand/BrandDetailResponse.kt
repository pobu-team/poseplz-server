package com.poseplz.server.ui.api.photobooth.brand

data class BrandDetailResponse(
    val brandId: String,
    val name: String,
    val description: String?,
    val logoUrl: String?,
    val homepageUrl: String?,
    val instagramUrl: String?,
    val photoBoothCount: Long,
)
