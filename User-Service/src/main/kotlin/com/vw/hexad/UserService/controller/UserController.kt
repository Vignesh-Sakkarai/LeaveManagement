package com.vw.hexad.UserService.controller

import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userService: UserService){

    @PostMapping(value = "/web/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    fun createUser(@RequestBody user: User): User{
        return userService.createUser(user)
    }

    @GetMapping(value = "/group/{userId}")
    fun getByUserId(@PathVariable(value = "userId") userId : Long): User {
        val user = userService.getByUserId(userId)
        user?.let { return user } ?: run{ throw UserNotFoundException("User Not Found for this provided userId::" + userId)}
    }
}