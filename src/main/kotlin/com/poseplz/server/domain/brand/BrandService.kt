package com.poseplz.server.domain.brand

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BrandService {
    fun getBrands(pageable: Pageable): Page<Brand>
    fun getBrand(brandId: Long): Brand
}
