package com.poseplz.server.infrastructure.spring

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.poseplz.server.ui.api.ApiResponse
import com.poseplz.server.ui.api.ResultCode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests(

            )
            .requestMatchers("/api/v1/**", "/actuator/**").permitAll()
            .requestMatchers("/login").permitAll()
            .requestMatchers("/favicon.ico", "/error").permitAll()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().disable()
            .oauth2Login()
            .and()
            .anonymous()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint { request, response, _ ->
                if (request.requestURI.startsWith("/api/v1")) {
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    response.outputStream.write(
                        jacksonObjectMapper().writeValueAsBytes(ApiResponse.failure(ResultCode.UNAUTHORIZED))
                    )
                } else {
                    response.sendRedirect("/login")
                }
            }
            .accessDeniedHandler { _, response, _ ->
                response.status = HttpStatus.FORBIDDEN.value()
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                response.outputStream.write(
                    jacksonObjectMapper().writeValueAsBytes(ApiResponse.failure(ResultCode.FORBIDDEN))
                )
            }
        return http.build()
    }

    @Bean
    fun poseplzAdminOidcUserService(): OAuth2UserService<OidcUserRequest, OidcUser> {
        return PoseplzAdminOidcUserService()
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SecurityConfig::class.java)
    }
}

