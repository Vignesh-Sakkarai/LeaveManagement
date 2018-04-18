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

    @GetMapping(value = "/group/{userId}")
    fun getByUserId(@PathVariable(value = "userId") userId : Long): User {
        val user = userService.getByUserId(userId)
        user?.let { return user } ?: run{ throw UserNotFoundException("User Not Found for this provided userId::" + userId)}
    }

    @PostMapping(value = "/group/validateLogin")
    @ResponseStatus(HttpStatus.OK)
    fun validateLogin(@RequestBody loginInfo: User): Boolean{
        val user = userService.getByUserName(loginInfo.userName)
        user?.let {
            val hashedValue = sha256HashingService.generateSecurePassword(loginInfo.passWord, sha256HashingService.convertStringTOByteArray(user.salt!!))
            when(user.passWord){
                hashedValue -> return true
                else -> throw UserNotFoundException("User Not Found for this provided userName::" + loginInfo.userName)
            }
        }?: run{throw UserNotFoundException("User Not Found for this provided userName::" + loginInfo.userName)}
    }

    @GetMapping("/group/{userName}")
    fun getByUserName(@PathVariable(value = "userName") userName: String) : User{
        val user = userService.getByUserName(userName)
        user?.let { return user } ?: run { throw UserNotFoundException("User Not Found for this provided userId::" + userName)}
    }
}