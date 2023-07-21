package com.sconnect.auth.configs

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig() : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3090","http://localhost:3000","http://localhost:9191","http://localhost:8082")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}