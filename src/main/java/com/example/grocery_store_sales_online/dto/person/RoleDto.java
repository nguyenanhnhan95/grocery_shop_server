package com.example.grocery_store_sales_online.dto.person;

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
public class RoleDto {
    @Size(max = 50,message = THIS_FIELD_TOO_LONG)
    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    private String name;
    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    @Size(max = 250,message = THIS_FIELD_TOO_LONG)
    private String description;

    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    private String alias;
    private Set<String> permissions = new HashSet<>();
}
