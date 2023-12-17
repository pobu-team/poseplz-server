package com.poseplz.server.domain.photobooth.brand

import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, Long>, BrandRepositoryCustom {
}
