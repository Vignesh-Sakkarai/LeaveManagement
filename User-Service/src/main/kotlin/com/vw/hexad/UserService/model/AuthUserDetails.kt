package com.vw.hexad.UserService.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

open class AuthUserDetails(user: User) : User(), UserDetails{

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun isEnabled(): Boolean {
        return false
    }

    override fun getUsername(): String {
        return super.userName
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun getPassword(): String {
        return super.PassWord
    }

    override fun isAccountNonExpired(): Boolean {
       return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

}