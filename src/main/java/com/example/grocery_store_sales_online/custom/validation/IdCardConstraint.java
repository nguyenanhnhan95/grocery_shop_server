package com.example.grocery_store_sales_online.custom.validation;

import com.example.grocery_store_sales_online.custom.validation.handle.EmailExistValidator;
import com.example.grocery_store_sales_online.custom.validation.handle.IdCardValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdCardValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IdCardConstraint {
    String message() default CommonConstants.THIS_FIELD_ALREADY_EXIST;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
