package com.example.grocery_store_sales_online.config;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.File.FileEntry;
import com.example.grocery_store_sales_online.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import static com.example.grocery_store_sales_online.utils.CommonConstants.SLASH;
import static com.example.grocery_store_sales_online.utils.CommonConstants.UNDER_SCOPE;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileConfiguration {
    private final ApplicationContext applicationContext;
    private final S3Service s3Service;
    @Value("${aws.s3.buckets.customer}")
    private String s3BucketsCustomer;
    @Value("${aws.s3.buckets.domain}")
    private String s3BucketsDomain;
    public String defaultDirectory() {
        log.info("CoreObject:defaultDirectory execution started.");
        try {
            return applicationContext.getEnvironment().getProperty("filestore.root")
                    + applicationContext.getEnvironment().getProperty("filestore.folder");
        }catch (Exception ex){
            log.error("Exception occurred while CoreObject:defaultDirectory not directory   , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FILE_DIRECTORY_FAIL.getLabel(),EResponseStatus.FILE_DIRECTORY_FAIL);
        }
    }

    private String defaultUrl() {
        log.info("CoreObject:defaultDirectory execution started.");
        try {
            return applicationContext.getEnvironment().getProperty("filestore.folder");
        }catch (Exception ex){
            log.error("Exception occurred while CoreObject:defaultUrl not directory folder   , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FILE_DIRECTORY_FAIL.getLabel(),EResponseStatus.FILE_DIRECTORY_FAIL);
        }
    }
    public FileEntry createFileEntry(MultipartFile media) {
        log.info("FileEntryService:createFileEntry execution started.");
        try {
            FileEntry file = new FileEntry();
            file.setName(media.getOriginalFilename());
            file.setExtension(getFileExtension(media.getOriginalFilename()));
            file.setContent(media);
            return file;
        }catch (Exception ex){
            log.error("Exception occurred while persisting FileEntryService:createFileEntry , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_FILE_LOAD_FAIL.getLabel(), EResponseStatus.AWS_FILE_LOAD_FAIL);
        }
    }
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return ""; // Nếu không có phần mở rộng
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
    private void createDirectoryNotExist(String directoryName) {
        try {
            File directory = new File(directoryName);
            if (directory.exists()) {
                directory.mkdir();
            }
        }catch (Exception ex){
            log.error("Exception occurred while persisting FileEntryService:createDirectoryNotExist fail , Exception message {}", ex.getMessage());
        }
    }
    private String getEnvironmentProperty(String property) {
        return Optional.ofNullable(applicationContext.getEnvironment().getProperty(property)).orElse("");
    }
    private String getDirectorySaveStore(FileEntry file) {
        if (file.isImage()) {
            return getEnvironmentProperty("filestore.folder.image");
        } else if (file.isVideo()) {
            return getEnvironmentProperty("filestore.folder.video");
        } else {
            return getEnvironmentProperty("filestore.folder.file");
        }
    }
    public void uploadToServer(FileEntry file) {
        log.info("FileEntryService:uploadToServer(file) execution started.");
        try {
            uploadToServer(file, defaultDirectory());
        } catch (Exception ex) {
            log.error("Exception occurred while FileEntryService:uploadToServer(file) not directory   , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FILE_UP_TO_SERVER_FAIL.getLabel(), EResponseStatus.FILE_UP_TO_SERVER_FAIL);
        }
    }

    public void uploadToServer(FileEntry file, String directory) {
        log.info("FileEntryService:uploadToServer(file) execution started.");
        try {
            if (file != null && file.getContent() != null) {
                String directorySaveStore=getDirectorySaveStore(file);
                String absolutePath = defaultDirectory().concat(directorySaveStore).concat(directory);
                createDirectoryNotExist(absolutePath);
                long millisecond = System.currentTimeMillis();
                String fileName = millisecond + UNDER_SCOPE + file.getName();
                Path filePath = Paths.get(SLASH+absolutePath, fileName);
//                file.getContent().transferTo(new File(filePath.toUri()));
                Files.createDirectories(filePath.getParent());
                // Ghi đè file nếu đã tồn tại
                Files.copy(file.getContent().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                s3Service.putObject(s3BucketsCustomer,absolutePath+fileName,file.getContent());
                file.setFileUrl(absolutePath+fileName);
            }
        } catch (Exception ex) {
            log.error("Exception occurred while FileEntryService:uploadToServer(file) not directory   , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FILE_UP_TO_SERVER_FAIL.getLabel(), EResponseStatus.FILE_UP_TO_SERVER_FAIL);
        }
    }
    public void deleteToServer(FileEntry file){
        log.info("FileEntryService:uploadToServer(file) execution started.");
        try {
            s3Service.deleteObject(s3BucketsCustomer,file.getFileUrl());
        }catch (Exception ex){
            throw new ServiceBusinessExceptional(EResponseStatus.AWS_DELETE_OBJECT_FAIL.getLabel(),EResponseStatus.AWS_DELETE_OBJECT_FAIL);
        }
    }
}
