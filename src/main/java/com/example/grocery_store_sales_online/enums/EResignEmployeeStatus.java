package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;
import lombok.Getter;

public enum EResignEmployeeStatus implements IEnumComboItem {
    RESIGNATION("Xin nghỉ việc"), RESIGNED("Bị nghỉ việc"), DIFFERENT("Lý do khác");
    EResignEmployeeStatus(String text){
        this.text=text;
    }
    @Getter
    private String text;
    @Override
    public String getLabel() {
        return text;
    }
}
