package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.FileNotEmptyConstraint;
import com.example.grocery_store_sales_online.custom.validation.FileTypeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FileTypeValidator implements ConstraintValidator<FileTypeConstraint, MultipartFile> {
    private List<String> contentTypes = new ArrayList<>();


    @Override
    public void initialize(FileTypeConstraint constraintAnnotation) {
        this.contentTypes = List.of(constraintAnnotation.contentType());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) return true;
        String contentType = value.getContentType();
        return StringUtils.isEmpty(contentType) || contentTypes.contains(contentType);
    }
}
