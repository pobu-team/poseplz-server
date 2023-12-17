package com.poseplz.server.domain.photobooth.brand

import org.springframework.data.domain.Pageable

interface BrandRepositoryCustom {
    fun findAllWithCount(pageable: Pageable): List<BrandDetailVo>
}
