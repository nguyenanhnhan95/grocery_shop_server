package com.example.grocery_store_sales_online.custom.validation;

import com.example.grocery_store_sales_online.custom.validation.handle.IdCardValidator;
import com.example.grocery_store_sales_online.custom.validation.handle.ListNotEmptyValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ListNotEmptyValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD ,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListNotEmptyConstraint {
    String message() default CommonConstants.THIS_COMBOBOX_ITEM_CANNOT_EMPTY;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
