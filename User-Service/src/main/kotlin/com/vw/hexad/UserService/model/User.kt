package com.vw.hexad.UserService.model

import javax.persistence.*

@Entity
data class User(
        @Column(name="userName")
        val userName: String,

        @Column(name="password")
        var password: String,

        @Column(name="salt")
        var salt: String?,

        @Column(name="emailAddress")
        val emailAddress: String?,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))  //Update the UsertTable as well bcos of Cascade property
        @JoinColumn(name="addressId", referencedColumnName = "addressId")
        var address : Address?,

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name="userId")
        val userId: Long = -1
){
    constructor(): this("","","","",null,-1)
}