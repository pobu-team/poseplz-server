package com.poseplz.server.ui.api.photobooth

import com.poseplz.server.domain.photobooth.Coordinates
import com.poseplz.server.ui.api.photobooth.brand.BrandResponse

data class PhotoBoothResponse(
    val photoBoothId: String,
    val brand: BrandResponse,
    val name: String?,
    val description: String?,
    val address: String?,
    val coordinates: Coordinates,
)
