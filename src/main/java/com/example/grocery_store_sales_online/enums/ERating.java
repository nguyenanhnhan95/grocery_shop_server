package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;
import lombok.Getter;

public enum ERating implements IEnumComboItem {
    One(1,"One"),
    Two(2,"Two"),
    Three(3,"Three"),
    Four(4,"Four"),
    Five(5,"Five");
    private ERating(Integer star, String text) {
        this.star = star;
        this.text = text;
    }

    @Getter
    private Integer star;

    private String text;

    @Override
    public String getLabel() {
        return text;
    }
}
