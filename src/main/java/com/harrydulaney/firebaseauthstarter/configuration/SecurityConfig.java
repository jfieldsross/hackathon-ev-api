package com.harrydulaney.firebaseauthstarter.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harrydulaney.firebaseauthstarter.security.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.sql.Timestamp;
import java.util.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final ObjectMapper objectMapper;
    public final TokenAuthenticationFilter tokenAuthenticationFilter;

    public SecurityConfig(ObjectMapper objectMapper, TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.objectMapper = objectMapper;
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return (httpServletRequest, httpServletResponse, e) -> {
            Map<String, Object> errorObject = new HashMap<>();
            int errorCode = 401;
            errorObject.put("message", "Unauthorized access of protected resource, invalid credentials");
            errorObject.put("error", HttpStatus.UNAUTHORIZED);
            errorObject.put("code", errorCode);
            errorObject.put("timestamp", new Timestamp(new Date().getTime()));
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(errorObject));
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(new ArrayList<>(Arrays.asList("http://localhost:4200")));
        configuration.setAllowedMethods(new ArrayList<>(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")));
        configuration.setAllowedHeaders(new ArrayList<>(Arrays.asList("Authorization", "Origin", "Content-Type", "Accept",
                "Accept-Encoding", "Accept-Language", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
                "Access-Control - Request-Method", "X-Requested-With", "X-Auth-Token", "X-Xsrf-Token",
                "Cache-Control", " Id-Token")));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(new ArrayList<>(Arrays.asList("X-Xsrf-Token")));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity
                .cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint())
                .and().authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated().and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}