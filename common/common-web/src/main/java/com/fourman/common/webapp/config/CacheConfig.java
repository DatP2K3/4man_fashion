package com.fourman.common.webapp.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Enables Spring Caching infrastructure.
 * Services use @Cacheable/@CacheEvict for data that rarely changes
 * (banners, categories, shop addresses).
 */
@Configuration
@EnableCaching
public class CacheConfig {
}
