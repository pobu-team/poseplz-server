package com.poseplz.server.domain.photobooth

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PhotoBoothServiceImpl(
    private val photoBoothRepository: PhotoBoothRepository,
) : PhotoBoothService {
    override fun getPhotoBooths(
        paegable: Pageable,
    ): Page<PhotoBooth> {
        return photoBoothRepository.findAll(paegable)
    }

    override fun getPhotoBooth(
        photoBoothId: Long,
    ): PhotoBooth {
        return photoBoothRepository.findByIdOrNull(photoBoothId)
            ?: throw PhotoBoothNotFoundException()
    }
}
