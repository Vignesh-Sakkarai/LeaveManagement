package com.vw.hexad.UserService.serviceImplTest

import com.vw.hexad.UserService.model.Address
import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.repository.AddressRepository
import com.vw.hexad.UserService.serviceImpl.AddressServiceImpl
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class AddressServiceImplTest{

    private val user = User("Vignesh", "1a93f75ace1ee8abac1aafbcd4fda614f1b3250c7bd66d72777197ebd59b8ff9","c846c1ed9185b6156621cfe87ba46d", "vignesh@gmail.com",
            Address("StralSunderRing", "Wolfsburg", "Germany", "38440", 1L), 1L)

    @InjectMocks
    lateinit var addressServiceImpl: AddressServiceImpl

    @Mock
    lateinit var addressRepository: AddressRepository

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `SHOULD_CREATE_ADDRESS_FOR_USER_WITH_VALID_USER`(){
        `when`(addressRepository.save(user.address!!)).thenReturn(user.address)
        addressServiceImpl.createAddressForUser(user)
    }
}