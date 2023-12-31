package com.poseplz.server.application.photobooth.brand

import com.poseplz.server.domain.photobooth.brand.Brand
import com.poseplz.server.domain.photobooth.brand.BrandDetailVo
import com.poseplz.server.domain.photobooth.brand.BrandService
import com.poseplz.server.domain.photobooth.brand.BrandWithCount
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class BrandApplicationService(
    private val brandService: BrandService,
) {
    fun getBrands(
        pageable: Pageable,
    ): Page<Brand> {
        return brandService.getBrands(pageable)
    }

    fun getBrandsWithCount(
        pageable: Pageable,
    ): List<BrandDetailVo> {
        return brandService.findAllWithCount(pageable)
    }

    fun getBrand(
        brandId: Long,
    ): Brand {
        return brandService.getBrand(brandId)
    }

    fun getBrandsWithCount(): List<BrandWithCount> {
        return brandService.getBrandsWithCount()
    }
}
