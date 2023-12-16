package com.poseplz.server.application.photobooth

import com.poseplz.server.application.photobooth.brand.toBrandResponse
import com.poseplz.server.domain.photobooth.Coordinates
import com.poseplz.server.domain.photobooth.PhotoBooth
import com.poseplz.server.domain.photobooth.PhotoBoothQueryVo
import com.poseplz.server.ui.api.photobooth.PhotoBoothQueryRequest
import com.poseplz.server.ui.api.photobooth.PhotoBoothResponse

fun PhotoBooth.toPhotoBoothResponse() = PhotoBoothResponse(
    photoBoothId = this.photoBoothId.toString(),
    name = this.name,
    description = this.description,
    address = this.address,
    brand = this.brand.toBrandResponse(),
    coordinates = this.coordinates,
)

fun PhotoBoothQueryRequest.toPhotoBoothQueryVo(): PhotoBoothQueryVo {
    val hasCoordinates = this.latitude != null && this.longitude != null
    return PhotoBoothQueryVo(
        brandIds = this.brandIds,
        distance = this.distance ?: if (hasCoordinates) 1000.0 else null,
        coordinates = if (hasCoordinates) Coordinates(this.latitude!!, this.longitude!!) else null,
    )
}
