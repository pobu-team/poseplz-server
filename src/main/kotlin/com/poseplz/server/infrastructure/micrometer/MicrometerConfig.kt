package com.poseplz.server.infrastructure.micrometer

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class MicrometerConfig(
    @Value("\${spring.application.name}")
    private val applicationName: String,
) {
    @Bean
    fun metricsCommonTags(): MeterRegistryCustomizer<MeterRegistry>? {
        return MeterRegistryCustomizer { registry: MeterRegistry ->
            registry.config().commonTags("application", applicationName)
        }
    }
}
