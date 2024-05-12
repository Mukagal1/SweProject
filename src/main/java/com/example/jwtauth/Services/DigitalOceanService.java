//package com.example.jwtauth.Services;
//
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import software.amazon.awssdk.core.sync.ResponseTransformer;
//import software.amazon.awssdk.services.s3.model.GetObjectRequest;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.net.URI;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Service
//public class DigitalOceanService {
//
//    @Value("${digitalocean.spaces.access-key-id}")
//    private String accessKeyId;
//
//    @Value("${digitalocean.spaces.secret-access-key}")
//    private String secretAccessKey;
//
//    @Value("${digitalocean.spaces.region}")
//    private String region;
//
//    @Value("${digitalocean.spaces.bucket}")
//    private String bucket;
//
//    @Value("${digitalocean.spaces.endpoint}")
//    private String endpoint;
//
//    public void uploadFile(File file, String key) throws IOException {
//        try (FileInputStream fis = new FileInputStream(file)) {
//            S3Client s3 = S3Client.builder()
//                    .region(Region.of(region))
//                    .endpointOverride(URI.create(endpoint))
//                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
//                    .build();
//
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(bucket)
//                    .key(key)
//                    .acl(ObjectCannedACL.PUBLIC_READ) // Set the ACL to public-read
//                    .build();
//
//            s3.putObject(putObjectRequest, RequestBody.fromInputStream(fis, file.length()));
//        }
//    }
//
//    public void deleteFile(String key) {
//        S3Client s3 = S3Client.builder()
//                .region(Region.of(region))
//                .endpointOverride(URI.create(endpoint))
//                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
//                .build();
//
//        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                .bucket(bucket)
//                .key(key)
//                .build();
//
//        s3.deleteObject(deleteObjectRequest);
//    }
//
//    public File downloadFile(String key) throws IOException {
//
//        S3Client s3 = S3Client.builder()
//                .region(Region.of(region))
//                .endpointOverride(URI.create(endpoint))
//                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
//                .build();
//
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(bucket)
//                .key(key)
//                .build();
//
//        Path tempFile = Files.createTempFile("download-", "-temp");
//        s3.getObject(getObjectRequest, ResponseTransformer.toFile(tempFile));
//
//        return tempFile.toFile();
//    }
//}