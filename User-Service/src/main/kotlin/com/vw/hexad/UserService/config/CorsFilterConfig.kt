package com.vw.hexad.UserService.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
class CorsFilterConfig{
    val Log: Logger = LoggerFactory.getLogger(CorsFilterConfig::class.java)

    @Bean
    fun corsFilter(): CorsFilter{
        var source: UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        var config: CorsConfiguration = CorsConfiguration()
        config.allowCredentials
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("PUT")
        config.addAllowedMethod("OPTIONS")
        config.addAllowedMethod("GET")
        config.addAllowedMethod("DELETE")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}