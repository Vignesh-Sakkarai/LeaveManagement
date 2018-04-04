package com.vw.hexad.UserService.controllerTest

import com.vw.hexad.UserService.controller.ExceptionController
import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.exception.ErrorResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
class ExceptionControllerTest {
    lateinit var mockMvc: MockMvc

    var errorResponse  = ErrorResponse(HttpStatus.NOT_FOUND.value(), "User Not Found for this provided userId::1")

    @InjectMocks
    lateinit var exceptionController: ExceptionController

    @Before
    fun setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(exceptionController).build()
    }

    @Test
    fun `SHOULD_HANDLE_USER_NOT_FOUND_EXCEPTION_GET_404_ERROR_CODE`(){
        try{
            throw UserNotFoundException("User Not Found for this provided userId::1")
        }catch(exception: UserNotFoundException){
            val handledErrorResponse:ErrorResponse = exceptionController.handleUserNotFoundException(exception)
            assertEquals(handledErrorResponse.errorCode, errorResponse.errorCode)
            assertEquals(handledErrorResponse.errorMessage, errorResponse.errorMessage)
        }
    }
}