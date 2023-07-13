package com.sconnect.apigateway.config

import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.WebFluxConfig
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebConfig() : WebFluxConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("**")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}