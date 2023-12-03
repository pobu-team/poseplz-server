package com.poseplz.server.domain.photobooth

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PhotoBoothService {
    fun getPhotoBooths(pageable: Pageable): Page<PhotoBooth>
    fun getPhotoBooth(photoBoothId: Long): PhotoBooth
    fun findBy(photoBoothSearchVo: PhotoBoothSearchVo, pageable: Pageable): Page<PhotoBooth>
    fun create(photoBoothCreateVo: PhotoBoothCreateVo): PhotoBooth
    fun update(photoBoothId: Long, photoBoothUpdateVo: PhotoBoothUpdateVo): PhotoBooth
}
