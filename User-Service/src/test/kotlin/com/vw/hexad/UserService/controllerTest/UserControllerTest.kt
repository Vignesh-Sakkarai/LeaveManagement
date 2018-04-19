package com.vw.hexad.UserService.controllerTest

import com.jayway.jsonpath.spi.json.GsonJsonProvider
import com.vw.hexad.UserService.controller.ExceptionController
import com.vw.hexad.UserService.controller.UserController
import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.Address
import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.model.exception.ErrorResponse
import com.vw.hexad.UserService.service.UserService
import com.vw.hexad.UserService.serviceImpl.SHA256HashingServiceImpl
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
class UserControllerTest{

    private val user = User("Vignesh", "1a93f75ace1ee8abac1aafbcd4fda614f1b3250c7bd66d72777197ebd59b8ff9","c846c1ed9185b6156621cfe87ba46d", "vignesh@gmail.com",
            Address("StralSunderRing", "Wolfsburg", "Germany", "38440", 1L), 1L)

    var errorResponse  = ErrorResponse(HttpStatus.NOT_FOUND.value(), "User Not Found for this provided userId::"+user.userId)

    lateinit var mockMvc: MockMvc

    @InjectMocks
    lateinit var userController: UserController

    @Mock
    private lateinit var userService: UserService

    @Before
    fun setupMock(){
        //Create the instance of this controller
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(ExceptionController()).build()
    }

    @Test
    fun `SHOULD_CREATE_USER_WITH_VALID_DATA_AND_VERIFIED_THE_STATUS_MATCH_WITH_IS_CREATED`(){
        Mockito.`when`(userService.createUser(user)).thenReturn(user)
        mockMvc.perform(post("/web/signup", user).contentType(MediaType.APPLICATION_JSON)
                .content(GsonJsonProvider().toJson(user))).andExpect(status().isCreated)
    }

    @Test
    fun `SHOULD_VALIDATE_LOGIN_WITH_THE_VALID_USER`(){
        userController.sha256HashingService =  SHA256HashingServiceImpl()
        Mockito.`when`(userService.getByUserName(user.userName)).thenReturn(user)
        val loginUser = User("Vignesh", "12345678","c846c1ed9185b6156621cfe87ba46d", "vignesh@gmail.com",
                Address("StralSunderRing", "Wolfsburg", "Germany", "38440", 1L), 1L)
        mockMvc.perform(post("/group/validateLogin").contentType(MediaType.APPLICATION_JSON)
                .content(GsonJsonProvider().toJson(loginUser))).andExpect(status().isOk)
        Mockito.verify(userService).getByUserName(user.userName)
    }

    @Test
    fun `SHOULD_VALIDATE_LOGIN_WITH_THE_INVALID_USER`(){
        userController.sha256HashingService =  SHA256HashingServiceImpl()
        Mockito.`when`(userService.getByUserName("InvalidUser")).thenThrow(UserNotFoundException("User Not Found for this provided user:: InvalidUser"))
        mockMvc.perform(post("/group/validateLogin").contentType(MediaType.APPLICATION_JSON)
                .content(GsonJsonProvider().toJson(user))).andExpect(status().isNotFound)
        Mockito.verify(userService).getByUserName(user.userName)
    }

    @Test
    fun `SHOULD_VALIDATE_LOGIN_WITH_THE_INVALID_PASSWORD`(){
        userController.sha256HashingService =  SHA256HashingServiceImpl()
        Mockito.`when`(userService.getByUserName(user.userName)).thenReturn(user)
        val loginUser = User("Vignesh", "invalidPassword","c846c1ed9185b6156621cfe87ba46d", "vignesh@gmail.com",
                Address("StralSunderRing", "Wolfsburg", "Germany", "38440", 1L), 1L)
        mockMvc.perform(post("/group/validateLogin").contentType(MediaType.APPLICATION_JSON)
                .content(GsonJsonProvider().toJson(loginUser))).andExpect(status().isNotFound)
        Mockito.verify(userService).getByUserName(user.userName)
    }
}