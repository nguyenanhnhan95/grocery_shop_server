package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;
import lombok.Getter;

public enum ENameRegister implements IEnumComboItem {
    EMAIL("Đăng nhập bằng email"),FACEBOOK("Đăng Nhập bằng facebook"),NAME("Nhập tên đăng nhập");
    ENameRegister(String text){
        this.text=text;
    }
    @Getter
    private String text;

    @Override
    public String getLabel() {
        return text;
    }
}
