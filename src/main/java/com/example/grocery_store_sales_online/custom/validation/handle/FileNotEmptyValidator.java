package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.FileNotEmptyConstraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
public class FileNotEmptyValidator extends BaseValidator implements ConstraintValidator<FileNotEmptyConstraint, Object> {


    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Object ob, ConstraintValidatorContext context) {
        if (ob == null) {
            return false;
        }
        try {
            if (ob instanceof MultipartFile) {
                return !((MultipartFile) ob).isEmpty();
            } else if (ob instanceof List) {
                List<MultipartFile> multipartFiles = (List<MultipartFile>) ob;
                return !multipartFiles.isEmpty();
            }
            return true;
        }catch (Exception ex){
            log.error("Exception occurred while persisting FileNotEmptyValidator:isValid to validate , Exception message {}", ex.getMessage());
            return false;
        }
    }
}
