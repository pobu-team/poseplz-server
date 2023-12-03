package com.poseplz.server.infrastructure.naver

import com.poseplz.server.domain.geocode.GeoCodeService
import com.poseplz.server.domain.photobooth.Coordinates
import org.springframework.stereotype.Component

@Component
class NaverGeoCodeService(
    private val naverApiClient: NaverApiClient,
) : GeoCodeService {
    override fun toCoordinates(address: String): Coordinates {
        val reverseGeoCode = naverApiClient.reverseGeoCode(address)
        if (reverseGeoCode.addresses.isEmpty()) {
            return Coordinates.empty()
        }
        return Coordinates(
            latitude = reverseGeoCode.addresses.first().y,
            longitude = reverseGeoCode.addresses.first().x,
        )
    }
}
