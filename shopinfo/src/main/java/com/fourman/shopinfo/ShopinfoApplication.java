package com.fourman.shopinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {"com.fourman.shopinfo", "com.fourman.common"})
@EnableJpaAuditing
public class ShopinfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopinfoApplication.class, args);
    }
}
