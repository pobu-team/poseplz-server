package com.poseplz.server.ui

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

        fun failure(): ApiResponse<Unit> = ApiResponse(
            code = ResultCode.INTERNAL_SERVER_ERROR,
            message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
            data = null,
        )
    }
}
