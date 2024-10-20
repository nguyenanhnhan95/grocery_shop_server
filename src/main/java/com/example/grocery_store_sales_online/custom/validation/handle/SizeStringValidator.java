package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.SizeStringConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class SizeStringValidator implements ConstraintValidator<SizeStringConstraint, String> {
    private int min;
    private int max;

    @Override
    public void initialize(SizeStringConstraint constraintAnnotation) {
        this.min = constraintAnnotation.sizeMin();
        this.max = constraintAnnotation.sizeMax();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isNotBlank(value)){
            if(value.length()>max || value.length()<min){
                return  false;
            }
        }
        return true;
    }
}
