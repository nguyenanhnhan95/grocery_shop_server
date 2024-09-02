package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.PhoneExistEditConstraint;
import com.example.grocery_store_sales_online.repository.employee.IEmployeeRepository;
import com.example.grocery_store_sales_online.repository.user.IUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneExistEditValidator implements ConstraintValidator<PhoneExistEditConstraint,String> {
    private final IEmployeeRepository employeeRepository;
    private final IUserRepository userRepository;

    public PhoneExistEditValidator(IEmployeeRepository employeeRepository, IUserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if(phone==null || phone.isEmpty()){
            return true;
        }
        if(userRepository.findByPhone(phone.trim()).isEmpty()){
            return false;
        }
        return userRepository.findByPhone(phone.trim()).isEmpty() && employeeRepository.findByPhone(phone.trim()).isEmpty();
    }
}
