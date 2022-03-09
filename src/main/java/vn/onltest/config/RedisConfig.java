package vn.onltest.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("User", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(90000)))
                .withCacheConfiguration("QuestionType", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(90000)))
                .withCacheConfiguration("Subject", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(90000)))
                .withCacheConfiguration("Role", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(30000)));
    }
}
