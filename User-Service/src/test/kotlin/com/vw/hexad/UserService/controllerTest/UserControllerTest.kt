package com.vw.hexad.UserService.controllerTest

import com.jayway.jsonpath.spi.json.GsonJsonProvider
import com.vw.hexad.UserService.controller.ExceptionController
import com.vw.hexad.UserService.controller.UserController
import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.Address
import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.model.exception.ErrorResponse
import com.vw.hexad.UserService.service.SHA256HashingService
import com.vw.hexad.UserService.service.UserService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
class UserControllerTest{

    private val user = User("Vignesh", "12345678","c846c1ed9185b6156621cfe87ba46d", "vignesh@gmail.com",
            Address("StralSunderRing", "Wolfsburg", "Germany", "38440", 1L), 1L)

    var errorResponse  = ErrorResponse(HttpStatus.NOT_FOUND.value(), "User Not Found for this provided userId::"+user.userId)

    lateinit var mockMvc: MockMvc

    @InjectMocks
    lateinit var userController: UserController

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var sha256HashingService: SHA256HashingService

    @Before
    fun setupMock(){
        //Create the instance of this controller
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(ExceptionController()).build()
    }

    @Test
    fun `SHOULD_CREATE_USER_WITH_VALID_DATA_AND_VERIFIED_THE_STATUS_MATCH_WITH_IS_CREATED`(){
        Mockito.`when`(userService.createUser(user)).thenReturn(user)
        mockMvc.perform(MockMvcRequestBuilders.post("/web/signup", user).contentType(MediaType.APPLICATION_JSON)
                .content(GsonJsonProvider().toJson(user))).andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `SHOULD_RETURN_THE_USER_OBJECT_FOR_THE_PROVIDED_USERID`(){
        Mockito.`when`(userService.getByUserId(user.userId)).thenReturn(user)
        mockMvc.perform(MockMvcRequestBuilders.get("/group/{userId}",user.userId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", Matchers.`is`("Vignesh")))
        Mockito.verify(userService).getByUserId(user.userId)
    }

    @Test
    fun `SHOULD_RETURN_GET_BY_ID_METHOD_STATUS_404_NOT_FOUND`() {
        Mockito.`when`(userService.getByUserId(user.userId)).thenThrow(UserNotFoundException("User Not Found for this provided userId::" + user.userId))
        mockMvc.perform(MockMvcRequestBuilders.get("/group/{userId}", user.userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.`is`(errorResponse.errorCode)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.`is`(errorResponse.errorMessage)))
        Mockito.verify(userService).getByUserId(user.userId)
    }

    @Test
    fun `SHOULD_VALIDATE_LOGIN_WITH_THE_PROVIDED_DATA`(){
        Mockito.`when`(userService.getByUserName("Vignesh")).thenReturn(user)
        mockMvc.perform(MockMvcRequestBuilders.post("/group/validateLogin").contentType(MediaType.APPLICATION_JSON)
                .content(GsonJsonProvider().toJson(user))).andExpect(MockMvcResultMatchers.status().isOk)
    }

}