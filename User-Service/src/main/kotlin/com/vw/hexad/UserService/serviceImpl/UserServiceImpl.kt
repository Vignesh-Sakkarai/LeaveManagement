package com.vw.hexad.UserService.serviceImpl

import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.repository.UserRepository
import com.vw.hexad.UserService.service.AddressService
import com.vw.hexad.UserService.service.SHA256HashingService
import com.vw.hexad.UserService.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserServiceImpl : UserService {

    val Log: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var addressService: AddressService

    @Autowired
    lateinit var sha256HashingService:SHA256HashingService

    override fun createUser(user: User): User {
        val byteArray: ByteArray = sha256HashingService.generateSalt()
        user.salt = sha256HashingService.convertByteArrayToHex(byteArray)
        user.password = sha256HashingService.generateSecurePassword(user.password, byteArray)
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

    override fun validateLogin(userName: String, password: String): Boolean {
        val user: User = getByUserName(userName)
        val salt: ByteArray = sha256HashingService.convertStringTOByteArray(password)
        if(user.password == sha256HashingService.generateSecurePassword(password, salt)){
            return true
        }
        return false
    }

    override fun getByUserName(userName: String): User {
        return userRepository.findByUserName(userName)
    }

}