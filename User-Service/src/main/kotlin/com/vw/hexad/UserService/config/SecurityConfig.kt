package com.vw.hexad.UserService.config

import com.vw.hexad.UserService.repository.AddressRepository
import com.vw.hexad.UserService.repository.UserRepository
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService

@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = arrayOf(UserRepository::class, AddressRepository::class))
@Configuration
class SecurityConfig(private val userDetailService: UserDetailsService,
                     private val passwordHashingAndMatcher: PasswordHashingAndMatcherConfig): WebSecurityConfigurerAdapter() {


    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailService)?.passwordEncoder(passwordHashingAndMatcher)
    }

    override fun configure(web: WebSecurity?) {
        super.configure(web)
    }

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()
        http?.authorizeRequests()?.antMatchers("/group/*")?.authenticated()
                ?.antMatchers(HttpMethod.OPTIONS, "/**")?.permitAll()?.anyRequest()?.permitAll()

        http?.headers()?.cacheControl()
    }

}