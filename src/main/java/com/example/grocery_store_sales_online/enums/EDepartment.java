package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;
import lombok.Getter;

public enum EDepartment implements IEnumComboItem {
    PRESIDENT("Giám đốc"),
    MANAGE("Quản lý"),
    ACCOUNTING("Kế toán"),
    Supervisor("Bảo vệ");
    EDepartment(String text){
        this.text=text;
    }
    @Getter
    private String text;
    @Override
    public String getLabel() {
        return null;
    }
}
