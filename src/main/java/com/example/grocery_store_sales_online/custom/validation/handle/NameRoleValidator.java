package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.components.ListNameERole;
import com.example.grocery_store_sales_online.custom.validation.NameRoleConstraint;
import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.enums.ERole;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class NameRoleValidator extends BaseValidator implements ConstraintValidator<NameRoleConstraint, RoleDto> {
    private final ListNameERole listNameERole;

    public NameRoleValidator(ListNameERole listNameERole) {
        this.listNameERole = listNameERole;
    }

    @Override
    public boolean isValid(RoleDto roleDto, ConstraintValidatorContext context) {
        try {
            boolean isName = roleDto.getName() != null && StringUtils.isNotBlank(roleDto.getName());
            boolean isAlias = roleDto.getAlias() != null && StringUtils.isNotBlank(roleDto.getAlias());
            if (isAlias) {
                if (isName) {
                    List<Map<String,String>> aliasMap = listNameERole.listNameRoleByERole(roleDto.getAlias());
                    if (aliasMap.isEmpty()) {
                        addMessageValidation(context, "alias", CommonConstants.THIS_FIELD_ENTER_FAIL);
                        return false;
                    }else {
                        boolean exists = aliasMap.stream()
                                .anyMatch(item -> item.get("name").equals(roleDto.getName()));

                        if (!exists) {
                            addMessageValidation(context, "name", CommonConstants.THIS_FIELD_ENTER_FAIL);
                            return false;
                        }
                    }
                }
                return true;
            }
            if(isName){
                addMessageValidation(context, "alias", CommonConstants.PLEASE_COMPLETE_STEP);
                return  false;
            }
            return true;

        } catch (Exception ex) {
            log.error("Exception occurred while persisting NameRoleValidator:isValid to validate , Exception message {}", ex.getMessage());
            return false;
        }
    }
}
