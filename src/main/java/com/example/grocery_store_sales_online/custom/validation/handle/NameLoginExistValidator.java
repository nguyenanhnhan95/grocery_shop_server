package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.NameLoginExistConstraint;
import com.example.grocery_store_sales_online.repository.employee.IEmployeeRepository;
import com.example.grocery_store_sales_online.repository.user.IUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameLoginExistValidator implements ConstraintValidator<NameLoginExistConstraint,String> {
    private final IEmployeeRepository employeeRepository;
    private final IUserRepository userRepository;

    public NameLoginExistValidator(IEmployeeRepository employeeRepository, IUserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String nameLogin, ConstraintValidatorContext context) {
        if(nameLogin==null || nameLogin.isEmpty()){
            return true;
        }
        return employeeRepository.findByNameLogin(nameLogin.trim()).isEmpty() && userRepository.findByNameLogin(nameLogin.trim()).isEmpty();
    }
}
