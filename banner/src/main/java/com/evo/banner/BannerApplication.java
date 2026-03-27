package com.evo.banner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.evo.banner", "com.evo.common"})
public class BannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BannerApplication.class, args);
    }
}
