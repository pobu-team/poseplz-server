package com.poseplz.server.domain.photobooth

import jakarta.persistence.Embeddable

@Embeddable
data class Coordinates(
    /**
     * 위도
     */
    var latitude: Double,
    /**
     * 경도
     */
    var longitude: Double,
) {
    companion object {
        fun empty(): Coordinates {
            return Coordinates(0.0, 0.0)
        }
    }
}
