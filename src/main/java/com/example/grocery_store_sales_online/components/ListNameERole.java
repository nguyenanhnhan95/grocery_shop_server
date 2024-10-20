package com.example.grocery_store_sales_online.components;

import com.example.grocery_store_sales_online.enums.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@Getter
public class ListNameERole {
    private final Map<String, Supplier<List<Map<String, String>>>> strategyMap = new HashMap<>();

    public ListNameERole() {
        // Use lambda expressions and method references to populate strategyMap
        strategyMap.put(ERole.ADMIN.getCode(), this::formatRoleAdmin);
        strategyMap.put(ERole.MANAGER.getCode(), this::formatRoleManage);
        strategyMap.put(ERole.USER.getCode(), this::formatRoleCustomer);
        strategyMap.put(ERole.EMPLOYEE.getCode(), this::formatRoleEmployee);
    }
    public List<Map<String, String>> listNameRoleByERole(String nameERole) {
        return strategyMap.getOrDefault(nameERole, ArrayList::new).get();
    }

    private List<Map<String,String>> formatRoleAdmin(){
        return Arrays.stream(EAdminRole.values())
                .map(status -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", status.getLabel());
                    return map;
                })
                .collect(Collectors.toList());
    }
    private List<Map<String,String>> formatRoleCustomer(){
        return Arrays.stream(ECustomerRole.values())
                .map(status -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", status.getLabel());
                    return map;
                })
                .collect(Collectors.toList());
    }
    private List<Map<String,String>> formatRoleEmployee(){
        return Arrays.stream(EEmployeeRole.values())
                .map(status -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", status.getLabel());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String,String>> formatRoleManage(){
        return Arrays.stream(EManageRole.values())
                .map(status -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", status.getLabel());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
