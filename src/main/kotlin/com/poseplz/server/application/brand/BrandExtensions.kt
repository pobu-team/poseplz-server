package com.poseplz.server.application.brand

import com.poseplz.server.domain.brand.Brand
import com.poseplz.server.ui.api.brand.BrandResponse

fun Brand.toBrandResponse() = BrandResponse(
    brandId = this.brandId,
    name = this.name,
    description = this.description,
    logoUrl = this.logoUrl,
    homepageUrl = this.homepageUrl,
    instagramUrl = this.instagramUrl,
)
