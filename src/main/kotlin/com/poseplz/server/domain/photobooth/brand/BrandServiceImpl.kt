package com.poseplz.server.domain.photobooth.brand

import com.poseplz.server.domain.photobooth.PhotoBoothRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BrandServiceImpl(
    private val brandRepository: BrandRepository,
    private val photoBoothRepository: PhotoBoothRepository,
) : BrandService {
    override fun getBrands(
        pageable: Pageable,
    ): Page<Brand> {
        return brandRepository.findAll(pageable)
    }

    override fun getBrand(
        brandId: Long,
    ): Brand {
        return brandRepository.findByIdOrNull(brandId)
            ?: throw BrandNotFoundException()
    }

    override fun getBrandsWithCount(): List<BrandWithCount> {
        return brandRepository.findAll()
            .map { BrandWithCount(it, photoBoothRepository.countByBrand_brandId(brandId = it.brandId)) }
    }
}
