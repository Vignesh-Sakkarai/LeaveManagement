package com.vw.hexad.UserService.config

import com.vw.hexad.UserService.service.SHA256HashingService
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordHashingAndMatcherConfig(private val sha256HashingService: SHA256HashingService) : PasswordEncoder{

    override fun encode(rawPassword: CharSequence?): String? {
        return  sha256HashingService.generateSecurePassword(rawPassword.toString(),
                    sha256HashingService.convertStringTOByteArray(UserContextInfo.getSalt()))
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        println("::::::::::::::::::::::::::" + rawPassword.toString() + "::::::::::" + encodedPassword)
        return rawPassword.toString() == encodedPassword
    }
}