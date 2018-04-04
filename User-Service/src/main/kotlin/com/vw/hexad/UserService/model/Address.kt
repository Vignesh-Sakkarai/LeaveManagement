package com.vw.hexad.UserService.model

import javax.persistence.*

@Entity
data class Address(
        @Column(name="streetName")
        val streetName: String,

        @Column(name="city")
        val city: String,

        @Column(name="country")
        val country: String,

        @Column(name="zip")
        val zip: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="addressId")
        val addressId: Long = -1
){
    constructor(): this("","","","",-1)
}