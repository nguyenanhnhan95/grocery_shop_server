package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.DateConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class DateValidator implements ConstraintValidator<DateConstraint, Date> {
    private int minAge;
    @Override
    public void initialize(DateConstraint constraintAnnotation) {
        this.minAge = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Date birthOfDate, ConstraintValidatorContext context) {
        if (birthOfDate == null) {
            return true; // Để validation @NotNull xử lý nếu cần
        }

        LocalDate birthDate = new java.sql.Date(birthOfDate.getTime()).toLocalDate();
        return Period.between(birthDate, LocalDate.now()).getYears() >= minAge;
    }
}
