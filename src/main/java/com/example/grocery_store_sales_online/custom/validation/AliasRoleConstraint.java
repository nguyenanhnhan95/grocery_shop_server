package com.example.grocery_store_sales_online.custom.validation;

import com.example.grocery_store_sales_online.custom.validation.handle.AliasRoleValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AliasRoleValidator.class)
@Target( { ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AliasRoleConstraint {
    String message() default CommonConstants.THIS_FIELD_SELECT_FAIL;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
