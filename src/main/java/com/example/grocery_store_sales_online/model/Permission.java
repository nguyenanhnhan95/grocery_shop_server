package com.example.grocery_store_sales_online.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Permission {
    public static final String SEPARATOR = ":";
    public static final String ALL = "*";

    private String id;
    private String name;
    private List<Scope> scopes = new ArrayList<>();

    public Map<String, String> getPermisionKeyMap() {
        Map<String, String> map = new HashMap<>();
        map.put(id + SEPARATOR + ALL, getName());
        scopes.forEach(scope -> {
            map.put(this.getId() + SEPARATOR + scope.getId(), scope.getName());
        });
        return map;
    }

}
