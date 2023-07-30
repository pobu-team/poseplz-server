package com.poseplz.server.infrastructure.spring.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.poseplz.server.infrastructure.spring.security.preauth.JwtAuthenticationProvider
import com.poseplz.server.infrastructure.spring.security.preauth.BearerPreAuthenticatedProcessingFilter
import com.poseplz.server.ui.api.ApiResponse
import com.poseplz.server.ui.api.ResultCode
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@EnableWebSecurity
@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests()
            .requestMatchers("/actuator/**").permitAll()
            .requestMatchers("/login").permitAll()
            .requestMatchers("/api/v1/auth/login").permitAll()
            .requestMatchers("/favicon.ico", "/error", "/webjars/**", "/css/**", "/js/**").permitAll()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers("/api/v1/members/me").hasAuthority(MEMBER_ROLE_NAME)
            .requestMatchers("/api/v1/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().disable()
            .oauth2Login()
            .and()
            .addFilterAt(jwtPreAuthenticatedProcessingFilter(), AbstractPreAuthenticatedProcessingFilter::class.java)
            .anonymous()
            .and()
            .cors()
            .configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
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

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf(
            // production
            "https://poseplz.com",
            "https://www.poseplz.com",
            "https://api.poseplz.com",
            "https://server.poseplz.com",
            // vercel
            "https://pobu-himyne.vercel.app",
            "https://pobu-pose-recommend.vercel.app",
            // local
            "http://localhost:3000",
            "http://localhost:8080",
        )
        configuration.allowedMethods = listOf(
            HttpMethod.HEAD,
            HttpMethod.GET,
            HttpMethod.POST,
            HttpMethod.PUT,
            HttpMethod.PATCH,
            HttpMethod.DELETE,
            HttpMethod.OPTIONS,
        ).map { it.name() }
        configuration.addAllowedHeader("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun jwtPreAuthenticatedProcessingFilter(): AbstractPreAuthenticatedProcessingFilter {
        return BearerPreAuthenticatedProcessingFilter().apply {
            setAuthenticationManager(ProviderManager(JwtAuthenticationProvider()))
        }
    }

    companion object {
        const val MEMBER_ROLE_NAME = "MEMBER"
    }
}

