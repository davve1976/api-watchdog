package com.apiwatchdog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")   // Viktigt! Alla endpoints
                        .allowedOrigins("https://api-watchdog-frontend.up.railway.app")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")   // Låt browsern skicka allt
                        .exposedHeaders("*")
                        .allowCredentials(false);  // Sätt false eftersom du inte använder cookies
            }
        };
    }
}
