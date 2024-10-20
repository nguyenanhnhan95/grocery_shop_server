package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.PhoneExistConstraint;
import com.example.grocery_store_sales_online.repository.user.impl.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneExistValidator implements ConstraintValidator<PhoneExistConstraint,String> {

    private final UserRepository userRepository;

    public PhoneExistValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if(phone==null || phone.isEmpty()){
            return true;
        }
        return userRepository.findByPhone(phone.trim()).isEmpty();
    }
}
