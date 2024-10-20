package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum ERole implements IEnumComboItem {
    ADMIN("ROLE_ADMIN","Admin"),
    USER("ROLE_USER","Khách Hàng"),
    MANAGER("ROLE_MANAGER","Quản lý"),
    EMPLOYEE("ROLE_EMPLOYEE","Nhân viên");
    private final String code;
    private final String label;
    ERole(String code,String text) {
        this.code = code;
        this.label =text;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
