package com.example.grocery_store_sales_online.custom.validation;

import com.example.grocery_store_sales_online.custom.validation.handle.DateValidator;
import com.example.grocery_store_sales_online.custom.validation.handle.FileTypeValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileTypeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD,ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileTypeConstraint {
    String[] contentType();
    String message() default CommonConstants.THIS_FILE_ERROR_EXTENSION;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
