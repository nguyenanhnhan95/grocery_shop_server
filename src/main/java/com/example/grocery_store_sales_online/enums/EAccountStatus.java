package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;
import lombok.Getter;

public enum EAccountStatus implements IEnumComboItem {
    ACTIVATED("Đã kích hoạt", "ACTIVATED"),
    INACTIVE("Chưa kích hoạt", "INACTIVE"),
    LOCK("Đã khóa", "LOCK");
    @Getter
    private String label;
    @Getter
    private String code;

    private EAccountStatus(String label, String code) {
        this.label = label;
        this.code = code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String getCode() {
        return code;
    }
}
