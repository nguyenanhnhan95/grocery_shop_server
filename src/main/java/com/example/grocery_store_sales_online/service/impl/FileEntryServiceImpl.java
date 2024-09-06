package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.config.FileConfiguration;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.File.FileEntry;
import com.example.grocery_store_sales_online.repository.file.FileEntryRepository;
import com.example.grocery_store_sales_online.service.IFileEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileEntryServiceImpl extends BaseServiceImpl implements IFileEntryService {
    private final FileEntryRepository fileEntryRepository;
    private final FileConfiguration fileConfiguration;
    @Override
    public List<FileEntry> findAllAble() {
        return fileEntryRepository.findAll();
    }

    @Override
    public FileEntry saveFile(MultipartFile media) {
        log.info("FileEntryService:saveModel(file) execution started.");
        try {
            FileEntry file = fileConfiguration.createFileEntry(media);
            if (file.noId()) {
                fileConfiguration.uploadToServer(file, "");
            }
            setMetaData(file);
            setPersonAction(file);
            return fileEntryRepository.saveModel(file);
        }catch (Exception ex){
            log.error("Exception occurred while persisting FileEntryService:saveModel(file) to database and server , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public FileEntry saveFile(MultipartFile media, String directory) {
        log.info("FileEntryService:saveModel(file,directory) execution started.");
        try {
            FileEntry file = fileConfiguration.createFileEntry(media);
            if (file.noId()) {
                fileConfiguration.uploadToServer(file,directory);
            }
            setMetaData(file);
            setPersonAction(file);
            return fileEntryRepository.saveModel(file);
        }catch (Exception ex){
            log.error("Exception occurred while persisting FileEntryService:saveModel(file,directory) to database and server , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public void deleteModel(Long id) {
        try {
            log.info("FileEntryService:deleteModel execution started.");
            FileEntry fileEntry = findById(id).orElseThrow();
            fileEntryRepository.deleteById(fileEntry.getId());
            fileConfiguration.deleteToServer(fileEntry);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting FileEntryService:deleteModel to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.DELETE_FAIL.getLabel(), EResponseStatus.DELETE_FAIL.getCode());
        }
    }

    @Override
    public Optional<FileEntry> findById(Long id) {
        try {
            log.info("FileEntryService:findById execution started.");
            return fileEntryRepository.findById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting FileEntryService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
}
