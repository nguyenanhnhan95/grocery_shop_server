package com.example.grocery_store_sales_online.custom.validation.handle;

import jakarta.validation.ConstraintValidatorContext;

public class BaseValidator {
    protected void addMessageValidation(ConstraintValidatorContext context, String property) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(property)
                .addConstraintViolation();
    }
    protected void addMessageValidation(ConstraintValidatorContext context, String property, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
    }
    protected void addMessageValidationNotProperty(ConstraintValidatorContext context,String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
    protected void addMessageValidation(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addConstraintViolation();
    }
}
