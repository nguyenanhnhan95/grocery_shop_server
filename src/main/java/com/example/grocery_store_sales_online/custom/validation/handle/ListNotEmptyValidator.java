package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.ListNotEmptyConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ListNotEmptyValidator implements ConstraintValidator<ListNotEmptyConstraint, List<?>> {

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        // Return true if the list is not null and not empty
        return value != null && !value.isEmpty();
    }
}