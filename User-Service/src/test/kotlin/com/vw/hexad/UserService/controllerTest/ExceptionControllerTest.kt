package com.vw.hexad.UserService.controllerTest

import com.vw.hexad.UserService.controller.ExceptionController
import com.vw.hexad.UserService.exception.UserExistException
import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.exception.ErrorResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.http.HttpStatus
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import javax.persistence.EntityNotFoundException

@RunWith(SpringJUnit4ClassRunner::class)
class ExceptionControllerTest {
    lateinit var mockMvc: MockMvc

    private val errorResponse  = ErrorResponse(HttpStatus.NOT_FOUND.value(), "User Not Found for this provided userId::1")

    private val jpaObjErrorResponse  = ErrorResponse(HttpStatus.NOT_FOUND.value(), "EntityNotFoundException for the provided userId")

    private val noEntityErrorResponse  = ErrorResponse(HttpStatus.NOT_FOUND.value(), "EntityNotFoundException for the provided userId")

    private val userExistErrorResponse  = ErrorResponse(HttpStatus.FOUND.value(), "UserName Already Exist, please provide a valid user name: Vignesh")

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

    @Test
    fun `SHOULD_HANDLE_JPA_OBJECT_RETREIEVAL_FAILURE_EXCEPTION_GET_404_ERROR_CODE`(){
        try{
            throw JpaObjectRetrievalFailureException(EntityNotFoundException())
        }catch(exception: JpaObjectRetrievalFailureException){
            val handledErrorResponse:ErrorResponse = exceptionController.handleJpaObjectRetrievalFailureException(exception)
            assertEquals(handledErrorResponse.errorCode, jpaObjErrorResponse.errorCode)
            assertEquals(handledErrorResponse.errorMessage, jpaObjErrorResponse.errorMessage)
        }
    }

    @Test
    fun `SHOULD_HANDLE_ENTITY_NOT_FOUND_EXCEPTION_GET_404_ERROR_CODE`(){
        try{
            throw EntityNotFoundException()
        }catch(exception: EntityNotFoundException){
            val handledErrorResponse:ErrorResponse = exceptionController.handleEntityNotFoundException(exception)
            assertEquals(handledErrorResponse.errorCode, noEntityErrorResponse.errorCode)
            assertEquals(handledErrorResponse.errorMessage, noEntityErrorResponse.errorMessage)
        }
    }

    @Test
    fun `SHOULD_HANDLE_USER_EXIST_EXCEPTION_GET_302_ERROR_CODE`(){
        try{
            throw UserExistException("UserName Already Exist, please provide a valid user name: Vignesh")
        }catch(exception: UserExistException){
            val handledErrorResponse:ErrorResponse = exceptionController.handleUserExistException(exception)
            assertEquals(handledErrorResponse.errorCode, userExistErrorResponse.errorCode)
            assertEquals(handledErrorResponse.errorMessage, userExistErrorResponse.errorMessage)
        }
    }

}