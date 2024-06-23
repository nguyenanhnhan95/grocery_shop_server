package com.example.grocery_store_sales_online.config;

import com.example.grocery_store_sales_online.model.Permission;
import com.example.grocery_store_sales_online.model.account.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class AuthorizationObject {
    private Set<Role> roles = new HashSet<>();
    private Set<Permission> permissions = new HashSet<>();
}
