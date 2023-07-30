package com.snapfolio.LogKafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
class LogKafkaApplication

fun main(args: Array<String>) {
	runApplication<LogKafkaApplication>(*args)
}
