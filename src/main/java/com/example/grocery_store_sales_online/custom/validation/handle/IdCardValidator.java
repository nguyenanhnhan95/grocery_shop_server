package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.IdCardConstraint;
import com.example.grocery_store_sales_online.repository.employee.IEmployeeRepository;
import com.example.grocery_store_sales_online.repository.user.IUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdCardValidator implements ConstraintValidator<IdCardConstraint,String> {
    private final IEmployeeRepository employeeRepository;
    private final IUserRepository userRepository;

    public IdCardValidator(IEmployeeRepository employeeRepository, IUserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String idCard, ConstraintValidatorContext context) {
        if(idCard==null || idCard.isEmpty()){
            return true;
        }
        return userRepository.findByIdCard(idCard.trim()).isEmpty() && employeeRepository.findByIdCard(idCard.trim()).isEmpty();
    }
}
