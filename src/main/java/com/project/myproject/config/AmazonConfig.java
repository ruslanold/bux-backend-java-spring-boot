package com.project.myproject.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

        @Value("${aws.access.key.id}")
        private String awsKeyId;

        @Value("${aws.access.key.secret}")
        private String awsKeySecret;

        @Value("${aws.region}")
        private String awsRegion;

        @Bean
        public AmazonS3 amazonS3() {
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.awsKeyId, this.awsKeySecret);
            return AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.fromName(this.awsRegion))
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .build();
        }


}
