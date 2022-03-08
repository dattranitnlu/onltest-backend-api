package vn.onltest.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:env.properties")
public class MinioConfig {
    @Value("${minio.access.name}")
    private String accessKey;

    @Value("${minio.access.secret-key}")
    private String accessSecret;

    @Value("${minio.url}")
    private String minioUrl;

    @Bean
    public MinioClient generateMinioClient() {
        try {
            return MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey, accessSecret)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
