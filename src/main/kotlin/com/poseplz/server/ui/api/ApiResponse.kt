package com.poseplz.server.ui.api

import org.springframework.data.domain.Page
import org.springframework.data.domain.Slice

data class ApiResponse<T>(
    val code: ResultCode,
    val message: String,
    val data: T?,
    val pagination: PageResponse?,
) {
    companion object {
        fun success(): ApiResponse<Unit> = ApiResponse(
            code = ResultCode.SUCCESS,
            message = "성공",
            data = null,
            pagination = null,
        )

        fun <T> success(data: T): ApiResponse<T> = ApiResponse(
            code = ResultCode.SUCCESS,
            message = "성공",
            data = data,
            pagination = null,
        )

        fun <T> success(data: List<T>): ApiResponse<List<T>> = ApiResponse(
            code = ResultCode.SUCCESS,
            message = "성공",
            data = data,
            pagination = PageResponse.from(data),
        )

        fun <T> success(data: Slice<T>): ApiResponse<List<T>> = ApiResponse(
            code = ResultCode.SUCCESS,
            message = "성공",
            data = data.content,
            pagination = PageResponse.from(data),
        )

        fun <T> success(data: Page<T>): ApiResponse<List<T>> = ApiResponse(
            code = ResultCode.SUCCESS,
            message = "성공",
            data = data.content,
            pagination = PageResponse.from(data),
        )

        fun failure(resultCode: ResultCode = ResultCode.INTERNAL_SERVER_ERROR): ApiResponse<Unit> = ApiResponse(
            code = resultCode,
            message = resultCode.defaultMessage,
            data = null,
            pagination = null,
        )
    }
}
