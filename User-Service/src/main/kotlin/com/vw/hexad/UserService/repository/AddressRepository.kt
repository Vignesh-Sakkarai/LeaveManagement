package com.vw.hexad.UserService.repository

import com.vw.hexad.UserService.model.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository: JpaRepository<Address, Long>