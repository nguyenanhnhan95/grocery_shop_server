package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.FileSizeConstraint;
import com.example.grocery_store_sales_online.custom.validation.FileTypeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator extends BaseValidator implements ConstraintValidator<FileSizeConstraint, MultipartFile> {
    private long maxSize;
    @Override
    public void initialize(FileSizeConstraint fileSizeConstraint) {
        this.maxSize=fileSizeConstraint.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || value.getSize() <= this.maxSize;
    }
}
