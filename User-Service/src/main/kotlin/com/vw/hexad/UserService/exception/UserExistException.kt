package com.vw.hexad.UserService.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FOUND)
class UserExistException(override val message: String): RuntimeException(message){
    companion object {
        private const val serialVersionUID:Long = 1L

    }
}