package com.vw.hexad.UserService.controller

import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.exception.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFoundException(ex: UserNotFoundException):ErrorResponse{
        var errorResponse  = ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message)
        return errorResponse
    }
}
