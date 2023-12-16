package com.poseplz.server.domain.photobooth

data class PhotoBoothQueryVo(
    val brandIds: List<Long> = emptyList(),
    val distance: Double? = 1000.0,
    val coordinates: Coordinates? = null,
)
