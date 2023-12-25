package com.poseplz.server.infrastructure.kakao

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class KakaoConfig {
    @Value("\${poseplz.kakao.api.admin-key}")
    lateinit var kakaoAdminSdkKey: String

    @Bean("kakaoRestTemplate")
    fun kakaoRestTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .setConnectTimeout(Duration.ofSeconds(1))
            .setReadTimeout(Duration.ofSeconds(3))
            .build()
    }

    @Bean("kakaoAdminRestTemplate")
    fun kakaoAdminRestTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .setConnectTimeout(Duration.ofSeconds(1))
            .setReadTimeout(Duration.ofSeconds(3))
            .additionalInterceptors(kakaoAdminApiHeaderInterceptor())
            .build()
    }

    @Bean
    fun kakaoAdminApiHeaderInterceptor(): ClientHttpRequestInterceptor {
        return ClientHttpRequestInterceptor { request, body, execution ->
            request.headers.set("Authorization", "KakaoAK ${kakaoAdminSdkKey}")
            request.headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
            execution.execute(request, body)
        }
    }
}
