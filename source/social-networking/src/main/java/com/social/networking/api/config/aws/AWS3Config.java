package com.social.networking.api.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.social.networking.api.constant.SocialNetworkingConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWS3Config {
    @Value("${cloud.aws.credentials.access.key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret.key}")
    private String secretKey;

    @Bean
    AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withRegion(SocialNetworkingConstant.REGION_STATIC)
                .build();
    }
}
