package com.poseplz.server.infrastructure.springdoc

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    servers = [Server(url = "/")]
)
@Configuration
class SpringdocConfig
