package com.vw.hexad.UserService.config

import com.vw.hexad.UserService.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordHashingAndMatcherConfig(private val userService: UserService) : PasswordEncoder{
    override fun encode(rawPassword: CharSequence?): String? {
        return rawPassword.toString()
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return true
    }
}