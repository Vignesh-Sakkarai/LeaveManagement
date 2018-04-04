package com.vw.hexad.UserService.serviceImplTest

import com.vw.hexad.UserService.service.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import javax.persistence.EntityNotFoundException

@RunWith(SpringJUnit4ClassRunner::class)
class UserServiceImplTest{

    @Mock
    lateinit var userService: UserService

    @Test(expected = JpaObjectRetrievalFailureException::class)
    fun `SHOULD_THROW_THE_EXCEPTION_WHEN_INVALID_USER_ID_IS_PASSED`(){
        Mockito.`when`(userService.getByUserId(1)).thenThrow(JpaObjectRetrievalFailureException::class.java)
        userService.getByUserId(1)
    }

}