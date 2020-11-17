package com.savantdegrees.todo.config

import com.savantdegrees.todo.common.AuditorAwareImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Profile("!test")
class DatabaseConfig {
	@Bean
	AuditorAware<String> auditorAware(){
		return new AuditorAwareImpl()
	}
}
