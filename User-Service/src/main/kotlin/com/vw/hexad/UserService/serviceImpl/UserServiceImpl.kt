package com.vw.hexad.UserService.serviceImpl

import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.repository.UserRepository
import com.vw.hexad.UserService.service.AddressService
import com.vw.hexad.UserService.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserServiceImpl(val userRepository: UserRepository, val addressService: AddressService) : UserService {

    val Log: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun createUser(user: User): User {
        addressService.createAddressForUser(user)
        return userRepository.save(user)
    }

    override fun getByUserId(userId: Long): User? {
        var user: User? = null
        try{
            user = userRepository.getOne(userId)
        }catch(ex: Exception){
            when(ex){
                is EntityNotFoundException ->{
                    Log.error("UserServiceImpl - getByUserId() : EntityNotFoundException" + ex.message)
                }is JpaObjectRetrievalFailureException ->{
                    Log.error("UserServiceImpl - getByUserId() : JpaObjectRetrievalFailureException" + ex.message)
                }else ->{
                    Log.error("UserServiceImpl - getByUserId() : Exception" + ex.message)
                }
            }
        }
        return user;
    }

}