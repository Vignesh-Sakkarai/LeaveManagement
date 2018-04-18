package com.vw.hexad.UserService.service

import com.vw.hexad.UserService.model.User
import org.springframework.stereotype.Service

@Service
interface AddressService{
    fun createAddressForUser(user: User)
}