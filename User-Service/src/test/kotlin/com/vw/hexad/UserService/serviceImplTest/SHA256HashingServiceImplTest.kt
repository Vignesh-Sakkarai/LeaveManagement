package com.vw.hexad.UserService.serviceImplTest

import com.vw.hexad.UserService.service.SHA256HashingService
import com.vw.hexad.UserService.serviceImpl.SHA256HashingServiceImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SHA256HashingServiceImplTest{

    lateinit var sha256HashingService: SHA256HashingService

    @Before
    fun setup(){
        sha256HashingService = SHA256HashingServiceImpl()
    }

    @Test
    fun `SHOULD_GENERATED_SALT_AND_VERIFIED_THE_TYPE_AS_BYTE_ARRAY_AND_SIZE_AS_16`(){
        Assert.assertEquals(sha256HashingService.generateSalt().size, ByteArray(size = 16).size)
    }

    @Test
    fun `SHOULD_CONVERT_BYTE_ARRAY_TO_HEX_AS_A_STRING`(){
        val salt: ByteArray = byteArrayOf(-56,70,-63,-19,-111,-123,-74,21,102,33,-49,-24,123,-92,109)
        var hexString: String = sha256HashingService.convertByteArrayToHex(salt)
        Assert.assertEquals("c846c1ed9185b6156621cfe87ba46d", hexString)
    }

    @Test
    fun `SHOULD_CONVERT_HEX_STRING_TO_BYTE_ARRAY`(){
        val salt: ByteArray = byteArrayOf(-56,70,-63,-19,-111,-123,-74,21,102,33,-49,-24,123,-92,109)
        val actualSalt: ByteArray = sha256HashingService.convertStringTOByteArray("c846c1ed9185b6156621cfe87ba46d")
        Assert.assertTrue(salt contentEquals  actualSalt)
    }

    @Test
    fun `SHOULD_GENERATE_SECURE_PASSWORD_VALIDATE_IT_AS_A_STRING`(){
        val salt: ByteArray = byteArrayOf(-56,70,-63,-19,-111,-123,-74,21,102,33,-49,-24,123,-92,109)
        var hashedPasswd: String = sha256HashingService.generateSecurePassword("12345678", salt)
        Assert.assertEquals("1a93f75ace1ee8abac1aafbcd4fda614f1b3250c7bd66d72777197ebd59b8ff9", hashedPasswd)
    }

}