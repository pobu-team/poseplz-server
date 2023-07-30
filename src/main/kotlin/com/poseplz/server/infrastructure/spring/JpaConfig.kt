package com.poseplz.server.infrastructure.spring

import com.poseplz.server.infrastructure.spring.security.SpringSecurityAuditorAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@Configuration
class JpaConfig {

    @Bean
    fun auditorAware(): SpringSecurityAuditorAware {
        return SpringSecurityAuditorAware()
    }
}
