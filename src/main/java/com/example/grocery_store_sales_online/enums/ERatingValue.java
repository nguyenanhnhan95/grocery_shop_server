package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum ERatingValue implements IEnumComboItem {
    Five(5,"Năm sao"),
    FOUR(4,"Bốn sao"),
    THREE(3,"Ba sao"),
    TWO(2,"Hai sao"),
    ONE(1,"Một sao");

    ERatingValue( int star,String text) {
        this.star = star;
        this.text = text;
    }

    public int getStar() {
        return star;
    }

    private String text;
    private int star;
    @Override
    public String getLabel() {
        return text;
    }
}
