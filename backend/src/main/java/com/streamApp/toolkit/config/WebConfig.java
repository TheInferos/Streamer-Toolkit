package com.streamApp.toolkit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private static final int CORS_MAX_AGE_SECONDS = 3600;

  public WebConfig() {
    // Default constructor
  }

  @Override
  public void addCorsMappings(@NonNull final CorsRegistry registry) {
    registry.addMapping("/api/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(CORS_MAX_AGE_SECONDS);
  }
}
