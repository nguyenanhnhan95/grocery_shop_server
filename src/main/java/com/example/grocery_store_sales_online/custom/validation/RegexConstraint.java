package com.example.grocery_store_sales_online.custom.validation;


import com.example.grocery_store_sales_online.components.constraint.PayloadRegex;
import com.example.grocery_store_sales_online.custom.validation.handle.RegexValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT;

@Documented
@Constraint(validatedBy = RegexValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RegexConstraint {
    boolean allowNull() ;
    String regex() ;
    String message() default THIS_FIELD_VALUE_NOT_FORMAT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
