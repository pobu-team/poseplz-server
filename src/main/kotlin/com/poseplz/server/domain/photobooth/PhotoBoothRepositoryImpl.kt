package com.poseplz.server.domain.photobooth

import com.poseplz.server.domain.photobooth.brand.QBrand
import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class PhotoBoothRepositoryImpl : QuerydslRepositorySupport(PhotoBooth::class.java), PhotoBoothRepositoryCustom {
    private val qPhotoBooth = QPhotoBooth.photoBooth
    private val qBrand = QBrand.brand

    override fun findBy(
        photoBoothSearchVo: PhotoBoothSearchVo,
        pageable: Pageable,
    ): Page<PhotoBooth> {
        val condition = BooleanBuilder()
            .and(qPhotoBooth.deleted.isFalse)
            .and(qBrand.deleted.isFalse)
        val brandIds = photoBoothSearchVo.brandIds
        if (brandIds.isNotEmpty()) {
            condition.and(qBrand.brandId.`in`(brandIds))
        }
        return from(qPhotoBooth)
            .innerJoin(qPhotoBooth.brand, qBrand)
            .where(condition).fetchJoin()
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let { PageImpl(it.results, pageable, it.total) }
    }

}
