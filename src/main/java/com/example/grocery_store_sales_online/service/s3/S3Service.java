package com.example.grocery_store_sales_online.service.s3;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service  {
    private final S3Client s3Client;
    @Value("${aws.s3.region}")
    private  String awsRegion;
    @Value("${aws.s3.access.key}")
    private String awsS3AccessKey;
    @Value("${aws.s3.secrete.key}")
    private String awsS3SecreteKey;
    @Value("${aws.s3.buckets.customer}")
    private String s3BucketsCustomer;
    private static ByteBuffer getRandomByteBuffer(int size){
        try {
            log.info("S3Service:getRandomByteBuffer execution started.");
            byte[] b = new byte[size];
            new Random().nextBytes(b);
            return ByteBuffer.wrap(b);
        }catch (Exception ex){
            log.error("Exception occurred while config S3Client:getRandomByteBuffer , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.CONFIG_AWS_FILE.getLabel(), EResponseStatus.CONFIG_AWS_FILE.getCode());
        }
    }
    public void putObject(String bucketName, String key, MultipartFile file){
        log.info("S3Service:putObject execution started.");
        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));
        }catch (Exception ex){
            log.error("Exception occurred while config S3Client:s3Client , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.CONFIG_AWS_FILE.getLabel(), EResponseStatus.CONFIG_AWS_FILE.getCode());
        }
    }

    public byte[] getObject(String bucketName,String key)  {
        log.info("S3Service:getObject execution started.");
        try{
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            ResponseInputStream<GetObjectResponse> res= s3Client.getObject(getObjectRequest);
            return res.readAllBytes();
        }catch (Exception ex){
            log.error("Exception occurred while config S3Client:getObject , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_FILE_LOAD_FAIL.getLabel(), EResponseStatus.AWS_FILE_LOAD_FAIL.getCode());
        }
    }

    public void deleteObject(String bucketName,String key){
        log.info("S3Service:deleteObject execution started.");
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        }catch (Exception ex){
            log.error("Exception occurred while config S3Client:getObject delete object , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_DELETE_OBJECT_FAIL.getLabel(), EResponseStatus.AWS_DELETE_OBJECT_FAIL.getCode());
        }
    }
    public String copyObject(String fromBucket, String key, String toBucket){
        log.info("S3Service:copyObject execution started.");
        try {
            CopyObjectRequest copyReq = CopyObjectRequest.builder()
                    .sourceBucket(fromBucket)
                    .sourceKey(key)
                    .destinationBucket(toBucket)
                    .destinationKey(key)
                    .build();
            CopyObjectResponse copyRes = s3Client.copyObject(copyReq);
            return copyRes.copyObjectResult().toString();
        }catch (Exception ex){
            log.error("Exception occurred while config S3Client:copyObject delete object , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_COPY_OBJECT.getLabel(), EResponseStatus.AWS_COPY_OBJECT.getCode());
        }
    }
    public List<String> getKeysByPrefix(String bucketName,String prefix) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(prefix)
                .build();

        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);

        return listObjectsV2Response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }
}
