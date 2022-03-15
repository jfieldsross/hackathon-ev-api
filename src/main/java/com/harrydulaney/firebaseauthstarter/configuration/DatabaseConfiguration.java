package com.harrydulaney.firebaseauthstarter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.harrydulaney.firebaseauthstarter.repository"})
public class DatabaseConfiguration {
}