package com.vw.hexad.UserService.service

import org.springframework.stereotype.Service

@Service
interface SHA256HashingService {
    fun generateSecurePassword(password: String) : String
}