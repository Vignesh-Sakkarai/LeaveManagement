package com.vw.hexad.UserService.controller

import com.vw.hexad.UserService.exception.UserExistException
import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.service.SHA256HashingService
import com.vw.hexad.UserService.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class UserController{

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var sha256HashingService: SHA256HashingService

    @PostMapping(value = "/web/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody user: User): User{
        if(userService.isUserExist(user.userName)){
            throw UserExistException("UserName Already Exist, please provide a valid user name: " + user.userName)
        }
        return userService.createUser(user)
    }

    @PostMapping(value = "/web/validateLogin")
    @ResponseStatus(HttpStatus.OK)
    fun user(@RequestBody loginInfo: User): User {
        return getByUserName(loginInfo.userName)
    }

    @GetMapping("/group/{userName}")
    fun getByUserName(@PathVariable(value = "userName") userName: String) : User{
        val user = userService.getByUserName(userName)
        user?.let { return user } ?: run { throw UserNotFoundException("User Not Found for this provided userId::" + userName)}
    }
}