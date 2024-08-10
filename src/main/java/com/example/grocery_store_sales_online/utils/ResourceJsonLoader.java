package com.example.grocery_store_sales_online.utils;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

@Slf4j
public class ResourceJsonLoader {
    public <T> T readValue(String filePath, Class<T> type) throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
        try {
            log.info("ResourceJsonLoader:readValue  execution started."+filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource(filePath);
//            File jsonFile = readMenuJsonFile(filePath);
//            return objectMapper.readValue(jsonFile, type);
            InputStream inputStream = resource.getInputStream();
            return objectMapper.readValue(inputStream, type);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ResourceJsonLoader:readValue read resources , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.READ_PATH_FILE.getLabel(), EResponseStatus.READ_PATH_FILE.getCode());
        }
    }


//    private File readMenuJsonFile(String filePath) throws URISyntaxException {
//        try {
//            log.info("ResourceJsonLoader:readValue execution started.");
//            URL resource = getClass().getClassLoader().getResource(filePath);
//            assert resource != null;
//            return new File(resource.toURI());
//        } catch (Exception ex) {
//            log.error("Exception occurred while persisting ResourceJsonLoader:readValue read resources , Exception message {}", ex.getMessage());
//            throw new ServiceBusinessExceptional(EResponseStatus.READ_PATH_FILE.getLabel(), EResponseStatus.READ_PATH_FILE);
//        }
//    }
}
