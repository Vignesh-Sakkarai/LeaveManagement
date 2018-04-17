package com.vw.hexad.UserService.controller

import com.vw.hexad.UserService.exception.UserExistException
import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.exception.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.persistence.EntityNotFoundException

@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFoundException(ex: UserNotFoundException):ErrorResponse{
        return ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message)
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleJpaObjectRetrievalFailureException(ex: JpaObjectRetrievalFailureException): ErrorResponse{
        return ErrorResponse(HttpStatus.NOT_FOUND.value(), "EntityNotFoundException for the provided userId")
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ErrorResponse{
        return ErrorResponse(HttpStatus.NOT_FOUND.value(), "EntityNotFoundException for the provided userId")
    }

    @ExceptionHandler(UserExistException::class)
    @ResponseStatus(HttpStatus.FOUND)
    fun handleUserExistException(ex: UserExistException): ErrorResponse{
        return ErrorResponse(HttpStatus.FOUND.value(), ex.message)
    }

}
