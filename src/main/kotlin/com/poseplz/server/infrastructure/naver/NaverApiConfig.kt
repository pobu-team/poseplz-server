package com.poseplz.server.infrastructure.naver

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate
import java.time.Duration


@Configuration
class NaverApiConfig {
    @Value("\${poseplz.naver.api.client-id}")
    lateinit var naverApiClientId: String
    @Value("\${poseplz.naver.api.client-secret}")
    lateinit var naverApiClientSecret: String

    @Bean("naverRestTemplate")
    fun naverRestTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .setConnectTimeout(Duration.ofSeconds(1))
            .setReadTimeout(Duration.ofSeconds(3))
            .interceptors(naverApiHeaderInterceptor())
            .build()
    }

    fun naverApiHeaderInterceptor(): ClientHttpRequestInterceptor {
        return ClientHttpRequestInterceptor { request, body, execution ->
            request.headers.set("X-NCP-APIGW-API-KEY-ID", naverApiClientId)
            request.headers.set("X-NCP-APIGW-API-KEY", naverApiClientSecret)
            request.headers.set("Accept", "application/json")
            execution.execute(request, body)
        }
    }
}
