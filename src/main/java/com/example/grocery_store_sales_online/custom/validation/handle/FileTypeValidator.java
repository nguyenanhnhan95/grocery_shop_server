package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.FileNotEmptyConstraint;
import com.example.grocery_store_sales_online.custom.validation.FileTypeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class FileTypeValidator implements ConstraintValidator<FileTypeConstraint, Object> {
    private List<String> contentTypes = new ArrayList<>();


    @Override
    public void initialize(FileTypeConstraint constraintAnnotation) {
        this.contentTypes = List.of(constraintAnnotation.contentType());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Object ob, ConstraintValidatorContext context) {
        if (ob == null) return true;

        try {
            if (ob instanceof MultipartFile) {
                String contentType = ((MultipartFile) ob).getContentType();
                return StringUtils.isEmpty(contentType) || contentTypes.contains(contentType);
            } else if (ob instanceof List) {
                List<MultipartFile> multipartFiles = (List<MultipartFile>) ob;
                for (MultipartFile each:multipartFiles) {
                    String contentType = ((MultipartFile) each).getContentType();
                    if(!contentTypes.contains(contentType)){
                        return false;
                    }
                }

            }
            return true;
        }catch (Exception ex){
            log.error("Exception occurred while persisting FileSizeValidator:isValid to validate , Exception message {}", ex.getMessage());
            return false;
        }
    }
}
