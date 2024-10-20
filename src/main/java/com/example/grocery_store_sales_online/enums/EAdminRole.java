package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum EAdminRole implements IEnumComboItem {
    ADMIN_SYSTEM("Admin");

    EAdminRole(String label) {
        this.label = label;
    }

    private String label;

    @Override
    public String getLabel() {
        return label;
    }
}
