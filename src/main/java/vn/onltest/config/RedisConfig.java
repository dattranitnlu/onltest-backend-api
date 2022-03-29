package vn.onltest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.Arrays;

@Configuration
@EnableCaching
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String redisServer;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Bean
    public HashOperations<String, String, Object> initializeHashOperations() {
        return redisTemplate().opsForHash();
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        Cache singleRole = new ConcurrentMapCache("singleRole");
        Cache roleList = new ConcurrentMapCache("roleList");
        Cache userList = new ConcurrentMapCache("userList");
        Cache subjectListNotPagination = new ConcurrentMapCache("subjectListNotPagination");
        Cache lecturerListNotPagination = new ConcurrentMapCache("lecturerListNotPagination");
        Cache studentListNotPagination = new ConcurrentMapCache("studentListNotPagination");

        cacheManager.setCaches(Arrays.asList(singleRole, roleList, userList, subjectListNotPagination, lecturerListNotPagination, studentListNotPagination));
        return cacheManager;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        int port = Integer.parseInt(redisPort);
        RedisStandaloneConfiguration redisStandaloneConfiguration
                = new RedisStandaloneConfiguration(redisServer, port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }
}

