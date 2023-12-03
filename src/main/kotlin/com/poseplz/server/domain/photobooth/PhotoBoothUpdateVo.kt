package com.poseplz.server.domain.photobooth

import com.poseplz.server.domain.photobooth.brand.Brand

data class PhotoBoothUpdateVo(
    val name: String?,
    val brand: Brand?,
    val description: String?,
    val imageUrl: String?,
    val address: String?,
    val coordinates: Coordinates?,
)
