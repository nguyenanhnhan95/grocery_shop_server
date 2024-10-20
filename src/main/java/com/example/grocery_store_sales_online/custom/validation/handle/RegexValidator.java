package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.RegexConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class RegexValidator implements ConstraintValidator<RegexConstraint,String> {
    private boolean allowNull;
    private String regex;
    @Override
    public void initialize(RegexConstraint constraintAnnotation) {
        this.regex= constraintAnnotation.regex();
        this.allowNull= constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (allowNull || StringUtils.isBlank(value)) {
            return true;
        }


        return value.matches(regex);
    }
}
