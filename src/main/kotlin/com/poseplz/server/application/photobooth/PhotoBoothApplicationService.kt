package com.poseplz.server.application.photobooth

import com.poseplz.server.domain.geocode.GeoCodeService
import com.poseplz.server.domain.photobooth.*
import com.poseplz.server.domain.photobooth.brand.BrandService
import com.poseplz.server.ui.admin.photobooth.PhotoBoothAddRequest
import com.poseplz.server.ui.admin.photobooth.PhotoBoothEditRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PhotoBoothApplicationService(
    private val photoBoothService: PhotoBoothService,
    private val brandService: BrandService,
    private val geocodeService: GeoCodeService,
) {
    fun getPhotoBooths(
        pageable: Pageable,
    ): Page<PhotoBooth> {
        return photoBoothService.getPhotoBooths(pageable)
    }

    fun findBy(
        photoBoothSearchVo: PhotoBoothSearchVo,
        pageable: Pageable,
    ): Page<PhotoBooth> {
        return photoBoothService.findBy(photoBoothSearchVo, pageable)
    }

    fun getPhotoBooth(
        photoBoothId: Long,
    ): PhotoBooth {
        return photoBoothService.getPhotoBooth(photoBoothId)
    }

    fun addPhotoBooth(
        photoBoothAddRequest: PhotoBoothAddRequest,
    ): PhotoBooth {
        return photoBoothService.create(
            photoBoothCreateVo = PhotoBoothCreateVo(
                name = photoBoothAddRequest.name,
                brand = brandService.getBrand(photoBoothAddRequest.brandId),
                description = photoBoothAddRequest.description,
                imageUrl = photoBoothAddRequest.logoImageUrl,
                address = photoBoothAddRequest.address,
                coordinates = photoBoothAddRequest.address?.let(geocodeService::toCoordinates) ?: Coordinates.empty()
            ),
        )
    }

    fun editPhotoBooth(
        photoBoothId: Long,
        photoBoothEditRequest: PhotoBoothEditRequest,
    ): PhotoBooth {
        return photoBoothService.update(
            photoBoothId = photoBoothId,
            photoBoothUpdateVo = PhotoBoothUpdateVo(
                name = photoBoothEditRequest.name,
                brand = photoBoothEditRequest.brandId?.let { brandService.getBrand(it) },
                description = photoBoothEditRequest.description,
                imageUrl = photoBoothEditRequest.logoImageUrl,
                address = photoBoothEditRequest.address,
                coordinates = photoBoothEditRequest.address?.let(geocodeService::toCoordinates) ?: Coordinates.empty(),
            ),
        )
    }
}
