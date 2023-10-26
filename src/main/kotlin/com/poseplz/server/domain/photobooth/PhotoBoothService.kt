package com.poseplz.server.domain.photobooth

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PhotoBoothService {
    fun getPhotoBooths(pageable: Pageable): Page<PhotoBooth>
    fun getPhotoBooth(photoBoothId: Long): PhotoBooth
}
