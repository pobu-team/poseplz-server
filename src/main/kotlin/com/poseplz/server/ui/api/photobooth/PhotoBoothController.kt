package com.poseplz.server.ui.api.photobooth

import com.poseplz.server.application.photobooth.PhotoBoothApplicationService
import com.poseplz.server.application.photobooth.toPhotoBoothQueryVo
import com.poseplz.server.application.photobooth.toPhotoBoothResponse
import com.poseplz.server.ui.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "포토부스", description = "포토부스 API")
@RequestMapping("/api/v1/photo-booths")
@RestController
class PhotoBoothController(
    private val photoBoothApplicationService: PhotoBoothApplicationService,
){

    @Operation(
        summary = "포토부스 목록 조회",
        description = "포토부스 목록을 조회합니다."
    )
    @GetMapping
    @PageableAsQueryParam
    fun getPhotoBooths(
        @ParameterObject photoBoothQueryRequest: PhotoBoothQueryRequest,
        @Parameter(hidden = true) pageable: Pageable,
    ): ApiResponse<List<PhotoBoothResponse>> {
        return ApiResponse.success(
            data = photoBoothApplicationService.getPhotoBooths(
                photoBoothQueryVo = photoBoothQueryRequest.toPhotoBoothQueryVo(),
                pageable = pageable
            ).map { it.toPhotoBoothResponse() },
        )
    }

    @Operation(
        summary = "포토부스 단건 조회",
        description = "포토부스 1개를 조회합니다."
    )
    @GetMapping("/{photoBoothId}")
    fun getPhotoBooth(
        @PathVariable photoBoothId: Long,
    ): ApiResponse<PhotoBoothResponse> {
        return ApiResponse.success(
            data = photoBoothApplicationService.getPhotoBooth(photoBoothId = photoBoothId).toPhotoBoothResponse(),
        )
    }

}
