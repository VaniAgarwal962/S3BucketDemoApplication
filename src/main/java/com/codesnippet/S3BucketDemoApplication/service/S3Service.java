package com.codesnippet.S3BucketDemoApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    // This method will upload the file to AWS S3 bucket
    public void uploadFile(MultipartFile file) throws IOException {
        s3Client.putObject(
                PutObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getOriginalFilename())
        .build(),
                RequestBody.fromBytes(file.getBytes()));
    }



    //reverse of uploadFile method,
    // it will download the file from S3 bucket and return it as a byte array
    public byte[] downloadFile(String key) {
        ResponseBytes<GetObjectResponse> objectBytes = s3Client.
                getObjectAsBytes(builder -> builder.bucket(bucketName).key(key).
                        build());
        return objectBytes.asByteArray();
    }

}
