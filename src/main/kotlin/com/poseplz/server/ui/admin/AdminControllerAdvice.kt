package com.poseplz.server.ui.admin

import org.springframework.ui.Model
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class AdminControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleBadRequestException(
        e: MethodArgumentNotValidException,
        model: Model,
    ): String {
        model.addAttribute("message", e.message)
        return "error"
    }
}
