package com.poseplz.server.ui.api

data class ApiResponse<T>(
    val code: ResultCode,
    val message: String,
    val data: T?,
) {
    companion object {
        fun success(): ApiResponse<Unit> = ApiResponse(
            code = ResultCode.SUCCESS,
            message = "성공",
            data = null,
        )

        fun <T> success(data: T): ApiResponse<T> = ApiResponse(
            code = ResultCode.SUCCESS,
            message = "성공",
            data = data,
        )

        fun <T> success(data: List<T>): ApiResponse<List<T>> = ApiResponse(
            code = ResultCode.SUCCESS,
            message = "성공",
            data = data,
        )

        fun failure(resultCode: ResultCode = ResultCode.INTERNAL_SERVER_ERROR): ApiResponse<Unit> = ApiResponse(
            code = resultCode,
            message = resultCode.defaultMessage,
            data = null,
        )
    }
}
