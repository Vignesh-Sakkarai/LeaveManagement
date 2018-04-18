package com.vw.hexad.UserService.config

import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.service.SHA256HashingService
import com.vw.hexad.UserService.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordHashingAndMatcherConfig(private val userService: UserService,
                                      private val sha256HashingService: SHA256HashingService) : PasswordEncoder{

    override fun encode(rawPassword: CharSequence?): String? {
        return  sha256HashingService.generateSecurePassword(rawPassword.toString(),
                    sha256HashingService.convertStringTOByteArray(UserContextInfo.getSalt()))
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return rawPassword.toString() == encodedPassword
    }
}