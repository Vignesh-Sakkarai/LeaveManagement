package com.vw.hexad.UserService.serviceImplTest

import com.vw.hexad.UserService.serviceImpl.SHA256HashingServiceImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
class SHA256HashingServiceImplTest{

    @Before


    @Test
    fun `SHOULD_GENERATED_SALT_AND_VERIFIED_THE_TYPE_AS_BYTE_ARRAY_AND_SIZE`(){
        val sha256HashingService = SHA256HashingServiceImpl()
        Assert.assertEquals(sha256HashingService.getSalt().size, ByteArray(size = 16).size)
        Assert.assertTrue(sha256HashingService.getSalt() is ByteArray)
    }

    @Test
    fun `SHOULD_GENERATE_SECURE_PASSWORD_VALIDATE_IT_AS_A_STRING`(){
        val sha256HashingService = SHA256HashingServiceImpl()
        val salt : ByteArray = sha256HashingService.getSalt()
        Assert.assertTrue(sha256HashingService.generateSecurePassword("$071eE211") is String)
    }

}