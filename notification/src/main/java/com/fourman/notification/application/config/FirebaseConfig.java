package com.fourman.notification.application.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FirebaseConfig {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        try (var serviceAccountStream = new ClassPathResource("firebase-service-account.json").getInputStream()) {
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccountStream);

            FirebaseOptions firebaseOptions =
                    FirebaseOptions.builder().setCredentials(googleCredentials).build();

            FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");

            return FirebaseMessaging.getInstance(app);
        }
    }
}
