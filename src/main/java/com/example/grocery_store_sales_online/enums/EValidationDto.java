package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum EValidationDto implements IEnumComboItem {
    NOT_BLANK("Vui lòng không để trống"),
    MAX("Nhập quá dài"),
    MIN("Nhập độ dài quá ngăn"),
    PATTERN("Nhập không phù hợp"),
    EXISTING("Đã tồn tại");

    EValidationDto(String text) {
        this.text = text;
    }

    private String text;
    @Override
    public String getLabel() {
        return this.text;
    }
}
