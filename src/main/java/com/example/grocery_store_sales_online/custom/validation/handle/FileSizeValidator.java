package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.FileSizeConstraint;
import com.example.grocery_store_sales_online.custom.validation.FileTypeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
public class FileSizeValidator extends BaseValidator implements ConstraintValidator<FileSizeConstraint, Object> {
    private long maxSize;
    @Override
    public void initialize(FileSizeConstraint fileSizeConstraint) {
        this.maxSize=fileSizeConstraint.maxSize();

    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Object ob, ConstraintValidatorContext context) {
        if(ob==null){
            return true;
        }
        try {
            if (ob instanceof MultipartFile) {
                return (((MultipartFile) ob).getSize() <= this.maxSize);
            } else if (ob instanceof List) {
                List<MultipartFile> multipartFiles = (List<MultipartFile>) ob;
                for (MultipartFile each:multipartFiles) {
                    if(each.getSize() > this.maxSize){
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
