package com.poseplz.server.domain.photobooth

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PhotoBoothRepositoryCustom {

    fun findBy(photoBoothSearchVo: PhotoBoothSearchVo, pageable: Pageable): Page<PhotoBooth>
}
