package com.example.grocery_store_sales_online.custom.validation;

import com.example.grocery_store_sales_online.custom.validation.handle.NameRoleValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameRoleValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameRoleConstraint {
    String message() default CommonConstants.THIS_FIELD_SELECT_FAIL;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
