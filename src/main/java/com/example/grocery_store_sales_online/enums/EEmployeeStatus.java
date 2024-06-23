package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;
import lombok.Getter;

public enum EEmployeeStatus implements IEnumComboItem {
    DOING("Đang làm việc"),
    BREAKTIME("Thôi việc"),
    DOAGAIN("Đang làm lại");
    EEmployeeStatus(String text){
        this.text=text;
    }
    @Getter
    private String text;
    @Override
    public String getLabel() {
        return text;
    }
}
