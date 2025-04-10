package com.evo.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients(
        basePackages = {
            "com.evo.common.storage.client",
            "com.evo.profile.infrastructure.adapter.keycloak",
        })
@SpringBootApplication(scanBasePackages = {"com.evo.profile", "com.evo.common"})
public class ProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }
}
