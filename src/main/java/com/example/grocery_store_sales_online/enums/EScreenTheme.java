package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum EModeTheme implements IEnumComboItem {
    Dark("Chế độ tối"),
    LIGHT("Chế độ sáng");

    EModeTheme(String text) {
        this.text = text;
    }

    private String text;
    @Override
    public String getLabel() {
        return this.text;
    }
}
