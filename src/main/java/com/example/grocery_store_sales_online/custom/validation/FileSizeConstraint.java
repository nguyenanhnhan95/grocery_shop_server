package com.example.grocery_store_sales_online.custom.validation;

import com.example.grocery_store_sales_online.custom.validation.handle.FileNotEmptyValidator;
import com.example.grocery_store_sales_online.custom.validation.handle.FileSizeValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD ,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileSizeConstraint {
    long maxSize();
    String message() default "File không lớn hơn {maxSize}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
