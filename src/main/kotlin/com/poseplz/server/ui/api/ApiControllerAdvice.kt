package com.poseplz.server.ui.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {
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
