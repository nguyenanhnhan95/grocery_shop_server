package com.example.grocery_store_sales_online.custom.validation;
import com.example.grocery_store_sales_online.custom.validation.handle.VariationOptionValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VariationOptionValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface VariationOptionConstraint {
    String message() default CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
