package com.streamApp.toolkit.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@TestConfiguration
public class TestConfig {

  private static final int MAX_AGE = 3600;

  @Bean
  @Primary
  public WebMvcConfigurer testWebMvcConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false) // Set to false for tests to avoid CORS issues
                .maxAge(MAX_AGE);
      }
    };
  }
}
