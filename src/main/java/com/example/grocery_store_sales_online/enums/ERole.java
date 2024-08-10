package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum ERole implements IEnumComboItem {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER"),
    EMPLOYEE("ROLE_EMPLOYEE");
    private String text;

    ERole(String text) {
        this.text = text;
    }

    @Override
    public String getLabel() {
        return this.text;
    }
}
