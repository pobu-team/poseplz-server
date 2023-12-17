package com.poseplz.server.domain.photobooth.brand

import com.poseplz.server.domain.photobooth.QPhotoBooth
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class BrandRepositoryImpl : QuerydslRepositorySupport(Brand::class.java), BrandRepositoryCustom {
    private val qBrand = QBrand.brand
    private val qPhotoBooth = QPhotoBooth.photoBooth

    override fun findAllWithCount(pageable: Pageable): List<BrandDetailVo> {
        val query = from(qBrand)
            .where(qBrand.deleted.isFalse)
            .leftJoin(qPhotoBooth).on(qBrand.eq(qPhotoBooth.brand))
            .groupBy(qBrand.brandId)
            .select(
                Projections.constructor(
                    BrandDetailVo::class.java,
                    qBrand.brandId,
                    qBrand.name,
                    qBrand.description,
                    qBrand.logoUrl,
                    qBrand.homepageUrl,
                    qBrand.instagramUrl,
                    qBrand.deleted,
                    qPhotoBooth.count().`as`("photoBoothCount")
                )
            )
        if (pageable.sort.isSorted) {
            pageable.sort.getOrderFor("photoBoothCount")?.let {
                query.orderBy(OrderSpecifier(if (it.direction.isAscending) Order.ASC else Order.DESC, qPhotoBooth.count()))
            }
        }
        return query.fetch()
    }

}
