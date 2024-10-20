package com.example.grocery_store_sales_online.custom.validation.handle;

import com.example.grocery_store_sales_online.components.Permission;
import com.example.grocery_store_sales_online.components.Scope;
import com.example.grocery_store_sales_online.config.AuthorizationProperties;
import com.example.grocery_store_sales_online.custom.validation.PermissionConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class PermissionValidator implements ConstraintValidator<PermissionConstraint, Set<String>> {
    private final AuthorizationProperties authorizationProperties;

    public PermissionValidator(AuthorizationProperties authorizationProperties) {
        this.authorizationProperties = authorizationProperties;
    }

    @Override
    public boolean isValid(Set<String> permissionDto, ConstraintValidatorContext context) {
        if (permissionDto == null || permissionDto.isEmpty()) {
            return true;
        }
        try {
            for (String per : permissionDto) {
                boolean flag = true;

                for (Permission permission : authorizationProperties.getPermissions()) {
                    if (!flag) {
                        break;
                    }
                    for (Scope scope : permission.getScopes()) {
                        if (scope.getId().equals(per)) {
                            flag = false;
                            break;
                        }
                    }

                }
                if (flag) {
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PermissionValidator:isValid to validate , Exception message {}", ex.getMessage());
            return false;
        }

    }
}
