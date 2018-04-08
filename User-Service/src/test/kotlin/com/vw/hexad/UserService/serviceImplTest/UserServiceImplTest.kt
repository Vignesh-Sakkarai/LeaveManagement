package com.vw.hexad.UserService.serviceImplTest

import com.vw.hexad.UserService.controller.ExceptionController
import com.vw.hexad.UserService.model.Address
import com.vw.hexad.UserService.service.UserService
import com.vw.hexad.UserService.serviceImpl.UserServiceImpl
import org.apache.catalina.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
class UserServiceImplTest{

    private val user = com.vw.hexad.UserService.model.User("Vignesh", "12345", "Test", "vignesh@gmail.com",
            Address("StralSunderRing", "Wolfsburg", "Germany", "38440", 1L), 1L)

    lateinit var mockMvc: MockMvc

    @InjectMocks
    lateinit var userServiceImp : UserServiceImpl

    @Mock
    lateinit var userService: UserService

    @Before
    fun setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(userServiceImp).setControllerAdvice(ExceptionController()).build()
    }

    @Test(expected = JpaObjectRetrievalFailureException::class)
    fun `SHOULD_THROW_THE_EXCEPTION_WHEN_INVALID_USER_ID_IS_PASSED`(){
        Mockito.`when`(userService.getByUserId(1)).thenThrow(JpaObjectRetrievalFailureException::class.java)
        userService.getByUserId(1)
    }

    @Test
    fun `SHOULD_FIND_THE_USER_BASED_ON_THE_VALID_USER_NAME`(){
        Mockito.`when`(userService.getByUserName("Vignesh")).thenReturn(user)
        Assert.assertEquals("vignesh@gmail.com", user.emailAddress)
    }

    @Test
    fun `SHOULD_THROW_EXCEPTION_TO_FIND_THE_USER_BASED_ON_THE_INVALID_USER_NAME`(){

    }

}