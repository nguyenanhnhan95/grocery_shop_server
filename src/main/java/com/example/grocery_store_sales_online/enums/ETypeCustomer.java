package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;
import lombok.Getter;

public enum ETypeCustomer implements IEnumComboItem {
    Normal("Khách hàng không đăng ký","normal"),
    Member("Thẻ thành viên","member"),
    SILVER("Thẻ bạc","silver"),
    Gold("Thẻ vàng","gold"),
    Diamond("Thẻ kim cương","diamond");
    @Getter
    private String text;
    @Getter
    private String style;
    private ETypeCustomer(String text,String style){
        this.text=text;
        this.style=style;
    }
    @Override
    public String getLabel() {
        return text;
    }
}
