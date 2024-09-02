package com.example.grocery_store_sales_online.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IS3Service {
    void putObject(String bucketName, String key, MultipartFile file);
    void putImage(String bucketName, String key, byte[] file);
    boolean checkKeyFileExisting(String keyFile);
    byte[] getObject(String bucketName,String key);
    void deleteObject(String bucketName,String key);
    String copyObject(String fromBucket, String key, String toBucket);
    List<String> getKeysByPrefix(String bucketName, String prefix);
}
