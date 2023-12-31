package com.poseplz.server.ui.api.photobooth.brand

import com.poseplz.server.application.photobooth.brand.BrandApplicationService
import com.poseplz.server.application.photobooth.brand.toBrandDetailResponse
import com.poseplz.server.application.photobooth.brand.toBrandResponse
import com.poseplz.server.ui.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "포토부스", description = "포토부스 브랜드 API")
@RequestMapping("/api/v1/brands")
@RestController
class BrandController(
    private val brandApplicationService: BrandApplicationService,
) {

    @Operation(
        summary = "포토부스 브랜드 목록 조회",
        description = "브랜드 목록을 조회합니다."
    )
    @GetMapping
    @PageableAsQueryParam
    fun getBrands(
        @Parameter(hidden = true) pageable: Pageable
    ): ApiResponse<List<BrandDetailResponse>> {
        return ApiResponse.success(
            data = brandApplicationService.getBrandsWithCount(pageable)
                .map { it.toBrandDetailResponse() },
        )
    }

    @Operation(
        summary = "포토부스 브랜드 단건 조회",
        description = "브랜드 1개를 조회합니다."
    )
    @GetMapping("/{brandId}")
    fun getBrand(
        @PathVariable brandId: Long,
    ): ApiResponse<BrandResponse> {
        return ApiResponse.success(
            data = brandApplicationService.getBrand(brandId = brandId)
                .run { this.toBrandResponse() }
        )
    }
}
