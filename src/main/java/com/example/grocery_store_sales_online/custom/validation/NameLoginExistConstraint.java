package com.example.grocery_store_sales_online.custom.validation;

import com.example.grocery_store_sales_online.custom.validation.handle.NameLoginExistValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameLoginExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NameLoginExistConstraint {
    String message() default CommonConstants.THIS_FIELD_ALREADY_EXIST;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
