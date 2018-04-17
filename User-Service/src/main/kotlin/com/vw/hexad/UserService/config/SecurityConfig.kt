package com.vw.hexad.UserService.config

import com.vw.hexad.UserService.repository.AddressRepository
import com.vw.hexad.UserService.repository.UserRepository
import com.vw.hexad.UserService.service.SHA256HashingService
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = [UserRepository::class, AddressRepository::class])
@Configuration
class SecurityConfig(private val userDetaiService: UserDetailsService,
                     private val sha256HashingService: SHA256HashingService,
                     private val passwordHashingAndMatcher: PasswordEncoder): WebSecurityConfigurerAdapter() {


    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetaiService)?.passwordEncoder(passwordHashingAndMatcher)
    }

    override fun configure(web: WebSecurity?) {
        super.configure(web)
    }

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()
        http?.authorizeRequests()?.antMatchers("/group/*")?.authenticated()
                ?.anyRequest()?.permitAll()
    }

}