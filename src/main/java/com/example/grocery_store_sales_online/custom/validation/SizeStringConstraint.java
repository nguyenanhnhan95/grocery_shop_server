package com.example.grocery_store_sales_online.custom.validation;


import com.example.grocery_store_sales_online.custom.validation.handle.SizeStringValidator;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArray;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SizeStringValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SizeStringConstraint {
    int sizeMin() default 0;
    int sizeMax() default 10;
    String message() default "Trường này có độ dài  {sizeMin} đến {sizeMax}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
