package com.poseplz.server.ui.api.photobooth.brand

data class BrandResponse(
    val brandId: Long,
    val name: String,
    val description: String,
    val logoUrl: String?,
    val homepageUrl: String?,
    val instagramUrl: String?,
)
