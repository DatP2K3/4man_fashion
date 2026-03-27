package com.fourman.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients(
        basePackages = {
            "com.fourman.common.storage.client",
            "com.fourman.profile.infrastructure.adapter.keycloak",
        })
@SpringBootApplication(scanBasePackages = {"com.fourman.profile", "com.fourman.common"})
public class ProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }
}
