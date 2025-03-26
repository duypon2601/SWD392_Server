//package com.restaurant.rms.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.messaging.FirebaseMessaging;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//
//@Slf4j
//@Configuration
//public class FirebaseConfig {
//
//    @Bean
//    public FirebaseApp firebaseApp() throws IOException {
//        try {
//            ClassPathResource resource = new ClassPathResource("hot-spot-c3a2d-firebase-adminsdk-fbsvc-70ead1ed32.json");
//            log.info("Loading Firebase credentials from: {}", resource.getPath());
//            if (!resource.exists()) {
//                log.error("Firebase credentials file not found at: {}", resource.getPath());
//                throw new IOException("Firebase credentials file not found");
//            }
//
//            GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
//            log.info("GoogleCredentials loaded successfully");
//
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(credentials)
//                    .build();
//
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseApp app = FirebaseApp.initializeApp(options);
//                log.info("FirebaseApp initialized successfully");
//                return app;
//            }
//            log.info("FirebaseApp already initialized, returning existing instance");
//            return FirebaseApp.getInstance();
//        } catch (Exception e) {
//            log.error("Failed to initialize FirebaseApp: {}", e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    @Bean
//    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
//        FirebaseMessaging messaging = FirebaseMessaging.getInstance(firebaseApp);
//        log.info("FirebaseMessaging bean created");
//        return messaging;
//    }
//}