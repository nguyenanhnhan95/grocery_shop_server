package com.example.grocery_store_sales_online.dto.person;

import com.example.grocery_store_sales_online.custom.validation.AliasRoleConstraint;
import com.example.grocery_store_sales_online.custom.validation.NameRoleConstraint;
import com.example.grocery_store_sales_online.custom.validation.PermissionConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_CANNOT_EMPTY_2;
import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_TOO_LONG;
@Setter
@Getter
@NameRoleConstraint
public class RoleDto {
    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    private String name;
    @Size(max = 250,message = THIS_FIELD_TOO_LONG)
    private String description;

    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    @AliasRoleConstraint
    private String alias;
    @PermissionConstraint
    private Set<String> permissions = new HashSet<>();
}
