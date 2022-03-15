package com.harrydulaney.firebaseauthstarter.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Register the App with Firebase to use SDK
 */
@Configuration
public class FirebaseConfig {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${firebase.database.url}")
    private String databaseUrl;

    @Value("${firebase.config}")
    private String config;

    @Primary
    @Bean
    public void initFirebase() {
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource(config).getInputStream();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        try {
            FirebaseOptions.Builder options = FirebaseOptions.builder();
            options.setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setDatabaseUrl(databaseUrl);
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options.build());
                LOG.info("FirebaseApp ------------------------------ Firebase services initialized for Backend.");
            }
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
