package com.vw.hexad.UserService.repository

import com.vw.hexad.UserService.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>{
    fun findByUserName(userName: String): User
}