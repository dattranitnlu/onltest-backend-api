package vn.onltest.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class RedisConfig {
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
}

