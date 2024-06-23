package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;
import lombok.Getter;

public enum EAccountStatus implements IEnumComboItem {
    ACTIVATED("Đã kích hoạt","Active"),
    INACTIVE("Chưa kích hoạt","Not Active"),
    Lock("Đã khóa","locked");
    @Getter
    private String text;
    @Getter
    private String style;
    private EAccountStatus(String text, String style){
        this.text=text;
        this.style=style;
    }
    @Override
    public String getLabel() {
        return text;
    }
}
