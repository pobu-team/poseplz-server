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

    override fun findBy(photoBoothSearchVo: PhotoBoothSearchVo, pageable: Pageable): Page<PhotoBooth> {
        return photoBoothRepository.findBy(photoBoothSearchVo, pageable)
    }

    @Transactional
    override fun create(photoBoothCreateVo: PhotoBoothCreateVo): PhotoBooth {
        return photoBoothRepository.save(
            PhotoBooth.from(
                photoBoothCreateVo = photoBoothCreateVo,
            ),
        )
    }

    @Transactional
    override fun update(
        photoBoothId: Long,
        photoBoothUpdateVo: PhotoBoothUpdateVo,
    ): PhotoBooth {
        val photoBooth = getPhotoBooth(photoBoothId)
        photoBooth.update(
            photoBoothUpdateVo = photoBoothUpdateVo,
        )
        return photoBooth
    }
}
