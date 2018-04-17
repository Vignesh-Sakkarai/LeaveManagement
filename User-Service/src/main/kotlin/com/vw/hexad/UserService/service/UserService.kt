package com.vw.hexad.UserService.service


import com.vw.hexad.UserService.model.User
import org.springframework.stereotype.Service

@Service
interface UserService{
    fun createUser(user: User) : User
    fun getByUserId(userId: Long): User?
    fun validateLogin(userName: String, password: String): Boolean
    fun getByUserName(userName: String): User?
    fun isUserExist(userName: String): Boolean
}