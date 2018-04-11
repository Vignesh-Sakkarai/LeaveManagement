package com.vw.hexad.UserService.serviceImplTest

import com.vw.hexad.UserService.model.Address
import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.repository.UserRepository
import com.vw.hexad.UserService.service.AddressService
import com.vw.hexad.UserService.service.UserService
import com.vw.hexad.UserService.serviceImpl.SHA256HashingServiceImpl
import com.vw.hexad.UserService.serviceImpl.UserServiceImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
class UserServiceImplTest{

    private val user = User("Vignesh", "cc3d7354059b37288cc41796658d3c4548235e92e00978bbaedd0e94bfe5545c","5e90d19eb3f832955908db38f76edfbf", "vignesh@gmail.com",
            Address("StralSunderRing", "Wolfsburg", "Germany", "38440", 1L), 1L)

    @InjectMocks
    private lateinit var userServiceImp : UserServiceImpl

    @Mock
    lateinit var userService: UserService

    @Mock
    private lateinit var addressService: AddressService

    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test(expected = JpaObjectRetrievalFailureException::class)
    fun `SHOULD_THROW_THE_EXCEPTION_WHEN_INVALID_USER_ID_IS_PASSED`(){
        `when`(userRepository.getOne(0)).thenThrow(JpaObjectRetrievalFailureException::class.java)
        userServiceImp.getByUserId(0)
        verify(userService.getByUserId(0))
    }

    @Test
    fun `SHOULD_CREATE_USER_WITH_VALID_USERDATA`(){
        `when`(userRepository.save(user)).thenReturn(user)
        doNothing().`when`(userServiceImp.addressService).createAddressForUser(user)
        userServiceImp.sha256HashingService = SHA256HashingServiceImpl()
        userServiceImp.createUser(user)
        verify(userServiceImp.addressService).createAddressForUser(user)
    }

    @Test
    fun `SHOULD VALIDATE LOGIN WITH VALID USER DATA`(){
        `when`(userServiceImp.getByUserName("Vignesh")).thenReturn(user)
        userServiceImp.sha256HashingService = SHA256HashingServiceImpl()
        val isValid: Boolean = userServiceImp.validateLogin("Vignesh", "071eE211")
        assertTrue(isValid)
    }

    @Test
    fun `SHOULD_RETURN_THE_USER_OBJECT_FOR_THE_PROVIDED_USERNAME`(){
        `when`(userRepository.findByUserName("Vignesh")).thenReturn(user)
        val expectedUser: User = userServiceImp.getByUserName("Vignesh")
        assertEquals(expectedUser.emailAddress, user.emailAddress)
    }

}