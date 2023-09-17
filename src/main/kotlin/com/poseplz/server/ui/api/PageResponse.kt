package com.poseplz.server.ui.api

import org.springframework.data.domain.Page
import org.springframework.data.domain.Slice

data class PageResponse(
    val hasNext: Boolean,
    val totalCount: Int?,
    val page: Int,
    val size: Int,
) {
    companion object {
        fun <T> from(list: List<T>): PageResponse {
            return PageResponse(
                hasNext = false,
                totalCount = list.size,
                page = 0,
                size = list.size,
            )
        }

        fun <T> from(slice: Slice<T>): PageResponse {
            return PageResponse(
                hasNext = slice.hasNext(),
                totalCount = null,
                page = slice.pageable.pageNumber,
                size = slice.pageable.pageSize,
            )
        }

        fun <T> from(page: Page<T>): PageResponse {
            return PageResponse(
                hasNext = page.hasNext(),
                totalCount = page.totalElements.toInt(),
                page = page.pageable.pageNumber,
                size = page.pageable.pageSize,
            )
        }
    }
}
