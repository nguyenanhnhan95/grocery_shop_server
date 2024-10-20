package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.AliasRoleConstraint;
import com.example.grocery_store_sales_online.enums.ERole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class AliasRoleValidator implements ConstraintValidator<AliasRoleConstraint,String> {
    @Override
    public boolean isValid(String alias, ConstraintValidatorContext context) {
        if(alias==null || StringUtils.isBlank(alias)){
            return false;
        }
        return Arrays.stream(ERole.values())
                .anyMatch(eRole -> alias.equals(eRole.getCode()));
    }
}
