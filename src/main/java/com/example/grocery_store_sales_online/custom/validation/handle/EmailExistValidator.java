package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.EmailExistConstraint;
import com.example.grocery_store_sales_online.repository.user.IUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailExistValidator implements ConstraintValidator<EmailExistConstraint,String> {
    private final IUserRepository userRepository;

    public EmailExistValidator( IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(email==null || email.isEmpty()){
            return true;
        }
        return userRepository.findByEmail(email.trim()).isEmpty();
    }
}
