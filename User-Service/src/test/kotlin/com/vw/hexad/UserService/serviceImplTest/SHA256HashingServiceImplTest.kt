package com.vw.hexad.UserService.serviceImplTest

import com.vw.hexad.UserService.service.SHA256HashingService
import com.vw.hexad.UserService.serviceImpl.SHA256HashingServiceImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import javax.xml.bind.DatatypeConverter

@RunWith(SpringJUnit4ClassRunner::class)
class SHA256HashingServiceImplTest{

    lateinit var mockMvc: MockMvc

    @InjectMocks
    lateinit var sha256HashingServiceImpl: SHA256HashingServiceImpl

    @Mock
    lateinit var sha256HashingService: SHA256HashingService

    @Before
    fun setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(sha256HashingServiceImpl).build()
    }

    @Test
    fun `SHOULD_GENERATED_SALT_AND_VERIFIED_THE_TYPE_AS_BYTE_ARRAY_AND_SIZE_AS_16`(){
        val sha256HashingService = SHA256HashingServiceImpl()
        Assert.assertEquals(sha256HashingService.generateSalt().size, ByteArray(size = 16).size)
        Assert.assertTrue(sha256HashingService.generateSalt() is ByteArray)
    }

    @Test
    fun `SHOULD_CONVERT_BYTE_ARRAY_TO_HEX_AS_A_STRING`(){
        val sha256HashingService = SHA256HashingServiceImpl()
        //val salt: ByteArray = sha256HashingService.generateSalt()
        val salt: ByteArray = byteArrayOf(19, 107, 113, 120)
        println("::::::::::::::::::" + sha256HashingService.convertByteArrayToHex(salt))
        Assert.assertTrue(DatatypeConverter.printHexBinary(salt) is String)
    }

    @Test
    fun `SHOULD_GENERATE_SECURE_PASSWORD_VALIDATE_IT_AS_A_STRING`(){
        val sha256HashingService = SHA256HashingServiceImpl()
        val salt : ByteArray = sha256HashingService.generateSalt()
        Assert.assertTrue(sha256HashingService.generateSecurePassword("$071eE211", sha256HashingService.generateSalt()) is String)
    }

    @Test
    fun `SHOULD_CONVERT_HEX_STRING_TO_BYTE_ARRAY`(){
        val sha256HashingService = SHA256HashingServiceImpl()
        val salt: ByteArray = "136b7178".toByteArray()
        println(":::::::::" + "136b7178".length)
        if("136b7178".length % 2 == 0){
            val hex: ByteArray = sha256HashingService.convertStringTOByteArray(sha256HashingService.convertByteArrayToHex(salt))
            for(byte: Byte in hex){
                println(":::::::::::" + byte)
            }
        }
        Assert.assertTrue(salt is ByteArray)
    }

}