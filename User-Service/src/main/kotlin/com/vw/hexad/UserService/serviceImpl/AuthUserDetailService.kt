package com.vw.hexad.UserService.serviceImpl

import com.vw.hexad.UserService.config.UserContextInfo
import com.vw.hexad.UserService.model.AuthUserDetails
import com.vw.hexad.UserService.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthUserDetailService(val userService: UserService): UserDetailsService{

    override fun loadUserByUsername(userName: String): UserDetails? {
        val user = userService.getByUserName(userName)!!
        UserContextInfo.setSalt(user.salt!!)
        return AuthUserDetails(user)
    }

}