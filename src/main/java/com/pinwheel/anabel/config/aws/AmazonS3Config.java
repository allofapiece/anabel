package com.pinwheel.anabel.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0.0
 */
@Configuration
public class AmazonS3Config {
    @Autowired
    private AWSCredentials credentials;

    @Value("${amazon.url}")
    private String endpointUrl;

    @Value("${amazon.bucket.name}")
    private String bucketName;

    @Bean
    public AWSCredentials credentials(
            @Value("${amazon.key.access}") String accessKey,
            @Value("${amazon.key.secret}") String secretKey
    ) {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3 s3client() {
        return new AmazonS3Client(credentials);
    }

    @Bean(name = "bucketName")
    public String bucketName() {
        return bucketName;
    }
}
