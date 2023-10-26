package com.poseplz.server.application.photobooth

import com.poseplz.server.application.photobooth.brand.toBrandResponse
import com.poseplz.server.domain.photobooth.PhotoBooth
import com.poseplz.server.ui.api.photobooth.PhotoBoothResponse

fun PhotoBooth.toPhotoBoothResponse() = PhotoBoothResponse(
    photoBoothId = this.photoBoothId.toString(),
    name = this.name,
    description = this.description,
    address = this.address,
    brand = this.brand.toBrandResponse(),
    coordinates = this.coordinates,
)
