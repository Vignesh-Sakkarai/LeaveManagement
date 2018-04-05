package com.vw.hexad.UserService.service

import org.springframework.stereotype.Service

@Service
interface SHA256HashingService {
    fun generateSecurePassword(password: String, salt: ByteArray) : String
    fun generateSalt(): ByteArray
    fun convertByteArrayToHex(byteArray: ByteArray): String
    fun convertStringTOByteArray(saltHexString: String): ByteArray
}