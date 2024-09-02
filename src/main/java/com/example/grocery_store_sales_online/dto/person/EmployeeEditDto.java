package com.example.grocery_store_sales_online.dto.person;

import com.example.grocery_store_sales_online.custom.validation.AccountStatusConstraint;
import com.example.grocery_store_sales_online.custom.validation.RoleNotEmptyConstraint;
import com.example.grocery_store_sales_online.custom.validation.RolesEmployeeConstraint;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class EmployeeEditDto extends PersonDto{

    @RoleNotEmptyConstraint
    @RolesEmployeeConstraint
    private List<Long> roles;
}
