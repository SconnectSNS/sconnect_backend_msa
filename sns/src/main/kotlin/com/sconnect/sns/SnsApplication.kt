package com.sconnect.sns

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class SnsApplication

fun main(args: Array<String>) {
	runApplication<SnsApplication>(*args)
}
