package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.custom.validation.AccountStatusConstraint;
import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountStatusValidator extends BaseValidator implements ConstraintValidator<AccountStatusConstraint,String> {
    @Override
    public boolean isValid(String codeStatus, ConstraintValidatorContext context) {
        try {
            if(codeStatus==null || codeStatus.isEmpty()){
                addMessageValidationNotProperty(context, CommonConstants.THIS_COMBOBOX_ITEM_CANNOT_EMPTY);
            }
            EAccountStatus.valueOf(codeStatus);
            return true;
        }catch (Exception ex){
            log.error("Exception occurred while persisting EmployeeRepository:search to read status account , Exception message {}", ex.getMessage());
            return false;
        }
    }
}
