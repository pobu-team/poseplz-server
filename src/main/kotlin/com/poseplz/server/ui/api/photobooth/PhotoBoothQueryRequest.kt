package com.poseplz.server.ui.api.photobooth

import io.swagger.v3.oas.annotations.Parameter

data class PhotoBoothQueryRequest(
    @Parameter(description = "브랜드 ID 목록")
    val brandIds: List<Long> = emptyList(),
    @Parameter(description = "거리 (미터)")
    val distance: Double?,
    @Parameter(description = "위도")
    val latitude: Double?,
    @Parameter(description = "경도")
    val longitude: Double?,
)
