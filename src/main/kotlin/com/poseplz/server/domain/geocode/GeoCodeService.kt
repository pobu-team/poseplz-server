package com.poseplz.server.domain.geocode

import com.poseplz.server.domain.photobooth.Coordinates

interface GeoCodeService {
    fun toCoordinates(address: String): Coordinates
}
