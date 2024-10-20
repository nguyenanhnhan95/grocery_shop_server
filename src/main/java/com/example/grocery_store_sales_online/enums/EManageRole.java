package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum EManageRole implements IEnumComboItem {
    MANAGE_REGION_ONE("Quản lý khu vực một"),
    MANAGE_REGION_TWO("Quản lý khu vực hai"),
    MANAGE_REGION_THREE("Quản lý khu vực ba"),;

    EManageRole(String label) {
        this.label = label;
    }

    private final String label;

    @Override
    public String getLabel() {
        return label;
    }
}
