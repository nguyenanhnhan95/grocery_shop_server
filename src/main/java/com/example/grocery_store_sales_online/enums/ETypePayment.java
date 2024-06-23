package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum ETypePayment implements IEnumComboItem {
    Banking("Chuyển khoảng");

    ETypePayment(String text) {
        this.text = text;
    }

    private String text;
    @Override
    public String getLabel() {
        return text;
    }
}
