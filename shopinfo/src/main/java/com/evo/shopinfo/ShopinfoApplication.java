package com.evo.shopinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {"com.evo.shopinfo", "com.evo.common"})
@EnableJpaAuditing
public class ShopinfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopinfoApplication.class, args);
    }
}
