package com.vw.hexad.UserService.serviceImpl

import com.vw.hexad.UserService.exception.UserExistException
import com.vw.hexad.UserService.exception.UserNotFoundException
import com.vw.hexad.UserService.model.User
import com.vw.hexad.UserService.repository.UserRepository
import com.vw.hexad.UserService.service.AddressService
import com.vw.hexad.UserService.service.SHA256HashingService
import com.vw.hexad.UserService.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var addressService: AddressService

    @Autowired
    lateinit var sha256HashingService:SHA256HashingService

    override fun createUser(user: User): User {
        val byteArray: ByteArray = sha256HashingService.generateSalt()
        user.salt = sha256HashingService.convertByteArrayToHex(byteArray)
        user.PassWord = sha256HashingService.generateSecurePassword(user.PassWord, byteArray)
        addressService.createAddressForUser(user)
        return userRepository.save(user)
    }

    override fun getByUserId(userId: Long): User? {
        return try{
            userRepository.getOne(userId)
        }catch(ex: Exception){
            when(ex){
                is EntityNotFoundException ->
                    throw EntityNotFoundException()
                is JpaObjectRetrievalFailureException ->
                    throw JpaObjectRetrievalFailureException(EntityNotFoundException())
                else ->
                    throw Exception()
            }
        }

    }

    override fun validateLogin(userName: String, password: String): Boolean {
        val user: User = getByUserName(userName)
        val salt: ByteArray = sha256HashingService.convertStringTOByteArray(user.salt!!)
        return user.PassWord == sha256HashingService.generateSecurePassword(password, salt)
    }

    override fun getByUserName(userName: String): User {
        return try{
            userRepository.findByUserName(userName)
        }catch(ex: EmptyResultDataAccessException){
            throw UserNotFoundException(userName)
        }
    }

    override fun isUserExist(userName: String): Boolean {
        return try{
            userRepository.findByUserName(userName)
            true
        }catch(ex: EmptyResultDataAccessException){
            return false
        }
    }

}