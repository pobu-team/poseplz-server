package com.poseplz.server.application.photobooth

import com.poseplz.server.domain.photobooth.PhotoBooth
import com.poseplz.server.domain.photobooth.PhotoBoothService
import com.poseplz.server.ui.api.photobooth.PhotoBoothResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PhotoBoothApplicationService(
    private val photoBoothService: PhotoBoothService,
) {
    fun getPhotoBooths(
        pageable: Pageable,
    ): Page<PhotoBoothResponse> {
        return photoBoothService.getPhotoBooths(pageable)
            .map { it.toPhotoBoothResponse() }
    }

    fun getPhotoBooth(
        photoBoothId: Long,
    ): PhotoBoothResponse {
        return photoBoothService.getPhotoBooth(photoBoothId)
            .let { it.toPhotoBoothResponse() }
    }
}
