package com.example.grocery_store_sales_online.config;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Slf4j
public class S3Config {
    @Value("${aws.s3.region}")
    private  String awsRegion;
    @Bean
    public S3Client s3Client(){
        try {
//            ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
            return S3Client.builder()
                    .region(Region.of(awsRegion))
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();
        }catch (Exception ex){
            log.error("Exception occurred while config S3Client:s3Client , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.CONFIG_AWS_FILE.getLabel(), EResponseStatus.CONFIG_AWS_FILE.getCode());
        }
    }

}
