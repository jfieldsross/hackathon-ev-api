package com.harrydulaney.firebaseauthstarter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("X-Auth-Token", "Access-Control-Allow-Headers",
                        "X-Requested-With, Content-Type, Authorization, Origin, Accept, " +
                                "Access-Control-Request-Method, Access-Control-Request-Headers");
    }
}