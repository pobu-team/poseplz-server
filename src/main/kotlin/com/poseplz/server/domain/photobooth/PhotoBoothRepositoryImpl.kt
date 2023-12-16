package com.poseplz.server.domain.photobooth

import com.poseplz.server.domain.photobooth.brand.QBrand
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.Expressions
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import java.math.BigDecimal

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

    override fun findByDistance(
        photoBoothQueryVo: PhotoBoothQueryVo,
        pageable: Pageable,
    ): Page<PhotoBooth> {
        val condition = BooleanBuilder()
            .and(qPhotoBooth.deleted.isFalse)

        val numberTemplate = Expressions.numberTemplate(
            BigDecimal::class.java,
            "ST_Distance_Sphere(POINT({0}, {1}), POINT({2}, {3}))",
            photoBoothQueryVo.coordinates?.longitude,
            photoBoothQueryVo.coordinates?.latitude,
            qPhotoBooth.coordinates.longitude,
            qPhotoBooth.coordinates.latitude,
        )
        photoBoothQueryVo.coordinates?.run {
            condition.and(
                numberTemplate.loe(photoBoothQueryVo.distance)
            )
        }
        if (photoBoothQueryVo.brandIds.isNotEmpty()) {
            condition.and(qPhotoBooth.brand.brandId.`in`(photoBoothQueryVo.brandIds))
        }

        val query = from(qPhotoBooth)
            .where(condition)

        return PageableExecutionUtils.getPage(
            querydsl!!.applyPagination(
                pageable,
                if (photoBoothQueryVo.coordinates != null) query.orderBy(numberTemplate.asc()) else query
            ).fetch(),
            pageable
        ) { query.fetchCount() }
    }
}
