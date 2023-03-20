package kookmin.software.capstone2023.timebank.infrastructure.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

class FeignConfiguration {
    @Bean
    @Profile("dev")
    fun feignLoggerLevel(): feign.Logger.Level {
        return feign.Logger.Level.FULL
    }
}