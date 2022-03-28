package com.example.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration

public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override

    public void addCorsMappings(CorsRegistry registry){
        //모든 경로에대해 오픈

        registry.addMapping("/**")
                //origin이 http:localhost:3000
                .allowedOriginPatterns("*")
                //GET,POST,PUT,PATCH,DELETE,OPTIONS 허용
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);

    }

}
