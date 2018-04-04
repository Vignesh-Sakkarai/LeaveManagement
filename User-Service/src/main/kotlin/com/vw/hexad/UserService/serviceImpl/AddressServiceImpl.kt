package com.vw.hexad.UserService.serviceImpl

import com.vw.hexad.UserService.model.Address
import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.repository.AddressRepository
import com.vw.hexad.UserService.service.AddressService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl: AddressService{

    @Autowired
    lateinit var addressRepository: AddressRepository

    override fun createAddressForUser(user: User) {
        val address = Address(user.address!!.streetName, user.address!!.country, user.address!!.city, user.address!!.zip)
        user.address = addressRepository.save(address)
    }

}