package com.example.grocery_store_sales_online.custom.validation.handle;
import com.example.grocery_store_sales_online.custom.validation.RolesEmployeeConstraint;
import com.example.grocery_store_sales_online.enums.ERole;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.repository.role.impl.RoleRepository;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class RolesEmployeeValidator extends BaseValidator implements ConstraintValidator<RolesEmployeeConstraint, List<Long>> {
    private final RoleRepository roleRepository;

    public RolesEmployeeValidator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean isValid(List<Long> Ids, ConstraintValidatorContext context) {
        try {
            if (Ids == null || Ids.isEmpty()) {
                return true;
            }
            for (Long id : Ids) {
                Optional<Role> role = roleRepository.findById(id);
                if (role.isEmpty()) {
                    addMessageValidation(context,"roles",CommonConstants.THIS_FIELD_ENTER_FAIL);
                    return false;
                }
                if(role.get().getAlias().equals(ERole.USER.getCode())){
                    addMessageValidation(context,"roles",CommonConstants.THIS_FIELD_NOT_CORRECT_FORMAT);
                    return false;
                }
            }
            return true;
        }catch (Exception ex){
            log.error("Exception occurred while persisting RolesValidator:isValid to validate , Exception message {}", ex.getMessage());
            return false;
        }
    }
}
