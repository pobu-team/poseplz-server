package com.poseplz.server.domain.brand

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BrandServiceImpl(
    private val brandRepository: BrandRepository,
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
}
