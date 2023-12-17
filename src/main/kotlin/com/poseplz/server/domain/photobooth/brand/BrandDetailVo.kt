package com.poseplz.server.domain.photobooth.brand

data class BrandDetailVo(
    val brandId: Long,
    var name: String,
    var description: String?,
    var logoUrl: String?,
    var homepageUrl: String?,
    var instagramUrl: String?,
    var deleted: Boolean = false,
    var photoBoothCount: Long,
)
