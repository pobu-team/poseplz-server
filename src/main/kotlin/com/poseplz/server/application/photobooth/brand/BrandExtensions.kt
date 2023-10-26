package com.poseplz.server.application.photobooth.brand

import com.poseplz.server.domain.photobooth.brand.Brand
import com.poseplz.server.ui.api.photobooth.brand.BrandResponse

fun Brand.toBrandResponse() = BrandResponse(
    brandId = this.brandId.toString(),
    name = this.name,
    description = this.description,
    logoUrl = this.logoUrl,
    homepageUrl = this.homepageUrl,
    instagramUrl = this.instagramUrl,
)
