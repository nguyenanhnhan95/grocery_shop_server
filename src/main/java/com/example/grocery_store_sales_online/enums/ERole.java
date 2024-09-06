package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum ERole implements IEnumComboItem {
    ADMIN("ROLE_ADMIN","Admin"),
    USER("ROLE_USER","Khách Hàng"),
    MANAGER("ROLE_MANAGER","Quản lý"),
    EMPLOYEE("ROLE_EMPLOYEE","Nhân viên");
    private final String label;
    private final String text;
    ERole(String label,String text) {
        this.label = label;
        this.text =text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
