package com.vw.hexad.UserService.controller

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
        return userService.createUser(user)
    }

    @GetMapping(value = "/group/{userId}")
    fun getByUserId(@PathVariable(value = "userId") userId : Long): User {
        val user = userService.getByUserId(userId)
        user?.let { return user } ?: run{ throw UserNotFoundException("User Not Found for this provided userId::" + userId)}
    }

    @PostMapping(value = "/group/validateLogin")
    @ResponseStatus(HttpStatus.OK)
    fun validateLogin(@RequestBody login: User): Boolean{
        val user = userService.getByUserName(login.userName)
        if(user != null){
            val hashedValue = sha256HashingService.generateSecurePassword(login.password, sha256HashingService.convertStringTOByteArray(user.salt!!))
            if(user.password == hashedValue){
                return true
            }else{
                // Can not handle it as a seperate exception for security reason(attains only if the password is wrong)
                throw UserNotFoundException("User Not Found for this provided userName::" + login.userName)
            }
        }else{
            throw UserNotFoundException("User Not Found for this provided userName::" + login.userName)
        }
    }
}