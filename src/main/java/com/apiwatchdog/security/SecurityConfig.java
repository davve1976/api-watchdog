package com.apiwatchdog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		// inga CSRF-kontroller
		.csrf(csrf -> csrf.disable())
		// ALLA requests är tillåtna
		.authorizeHttpRequests(auth -> auth
				.anyRequest().permitAll()
				)
		// stäng av basic auth och login-sida
		.httpBasic(httpBasic -> httpBasic.disable())
		.formLogin(form -> form.disable());

		return http.build();
	}
}
