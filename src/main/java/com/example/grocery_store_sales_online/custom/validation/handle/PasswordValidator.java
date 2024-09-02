package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.PasswordConstraint;
import com.example.grocery_store_sales_online.dto.person.PersonDto;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
@Slf4j
public class PasswordValidator extends BaseValidator implements ConstraintValidator<PasswordConstraint, PersonDto> {
    @Override
    public boolean isValid(PersonDto personDto, ConstraintValidatorContext context) {
        try {
            boolean isPassword= StringUtils.isBlank(personDto.getPassword());
            boolean isConfirmPassword = StringUtils.isBlank(personDto.getConfirmPassword());
            if(isPassword){
                if(!isConfirmPassword){
                    addMessageValidation(context,"password", CommonConstants.PLEASE_COMPLETE_STEP);
                    return false;
                }
            }else {
                if(!personDto.getConfirmPassword().equals(personDto.getPassword())){
                    addMessageValidation(context,"confirmPassword", CommonConstants.THIS_FIELD_CONFIRM_NOT_MATCH);
                    return false;
                }
            }
            return true;
        }catch (Exception ex){
            log.error("Exception occurred while persisting PasswordValidator:isValid to validate , Exception message {}", ex.getMessage());
            return false;
        }
    }
}
