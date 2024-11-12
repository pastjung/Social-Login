package com.inha.springbootapp.global.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {
    Dotenv dotenv = Dotenv.load();
    private final String reactURL = dotenv.get("CORS_ReactURL");

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                                            // 모든 경로에 대해 CORS를 허용
                .allowedOrigins(reactURL)                                     // React 애플리케이션의 URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")    // 허용할 HTTP 메서드
                .allowedHeaders("*")                                          // 모든 헤더 허용
                .allowCredentials(true);                                      // 자격 증명(쿠키 등)을 허용할지 여부
    }
}