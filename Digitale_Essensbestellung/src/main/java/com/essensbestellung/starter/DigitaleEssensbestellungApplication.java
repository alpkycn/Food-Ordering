package com.essensbestellung.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
@EntityScan(basePackages = { "com.essensbestellung" })
@EnableJpaRepositories(basePackages = { "com.essensbestellung" })
@ComponentScan(basePackages = { "com.essensbestellung" })
public class DigitaleEssensbestellungApplication {

    @Autowired
    private Environment environment;

    // CORS configuration via WebMvcConfigurer
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String url = environment.getProperty("whitelisted.cors.url");
                registry.addMapping("/**")
                        .allowedOrigins(url != null ? url : "http://localhost:8081") // Default to localhost if not specified
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DigitaleEssensbestellungApplication.class, args);
    }
}