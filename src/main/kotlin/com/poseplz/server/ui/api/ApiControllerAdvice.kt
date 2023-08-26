package com.poseplz.server.ui.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException

@RestControllerAdvice
class ApiControllerAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMaxUploadSizeExceededException(e: MaxUploadSizeExceededException): ApiResponse<Unit> {
        log.error("Max upload size exceeded.", e)
        return ApiResponse.failure(
            resultCode = ResultCode.MAX_UPLOAD_SIZE_EXCEEDED,
        )
    }


    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): ApiResponse<Unit> {
        log.error("Internal server error.", e)
        return ApiResponse.failure()
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ApiControllerAdvice::class.java)
    }
}
