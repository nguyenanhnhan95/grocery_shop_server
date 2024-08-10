package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum EScreenTheme implements IEnumComboItem {
    DARK("DARK"),
    LIGHT("LIGHT");

    EScreenTheme(String text) {
        this.text = text;
    }

    private String text;
    @Override
    public String getLabel() {
        return this.text;
    }
}
