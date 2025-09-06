package com.recipe.book.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("Access-Control-Max-Age", "Authorization", "Content-Type", "Content-Encoding",
                        "Content-Length", "Content-Disposition", "Accept-Encoding", "Accept")
                .allowedOrigins("http://localhost:3000", "http://192.168.0.10:3000", "http://192.168.0.7:23004")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .exposedHeaders("Access-Control-Max-Age", "Authorization", "Content-Type", "Content-Encoding",
                        "Content-Length", "Content-Disposition", "Accept-Encoding", "Accept")
                .allowCredentials(true)
                .maxAge(1800);
    }
}
