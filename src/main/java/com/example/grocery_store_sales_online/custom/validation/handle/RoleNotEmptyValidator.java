package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.RoleNotEmptyConstraint;
import com.example.grocery_store_sales_online.repository.role.impl.RoleRepository;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RoleNotEmptyValidator implements ConstraintValidator<RoleNotEmptyConstraint, List<Long>> {

    @Override
    public boolean isValid(List<Long> Ids, ConstraintValidatorContext context) {
        if (Ids == null || Ids.isEmpty()) {
            return false;
        }
        return true;
    }
}
