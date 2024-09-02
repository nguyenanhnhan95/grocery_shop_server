package com.example.grocery_store_sales_online.custom.validation;

import com.example.grocery_store_sales_online.custom.validation.handle.RoleNotEmptyValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleNotEmptyValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleNotEmptyConstraint {
    String message() default CommonConstants.THIS_COMBOBOX_ITEM_CANNOT_EMPTY;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
