package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum EOrderStatus implements IEnumComboItem {
    WaitForPay("Chờ thanh toán"),
    Transport("Vận chuyển"),
    WaitForDelivery("Chờ giao hàng"),
    Completed("Hoàn thành"),
    Canceled("Đã hủy"),
    ReturnRefund("Trả hàng/Hoàn tiền");

    EOrderStatus(String text) {
        this.text = text;
    }

    private String text;
    @Override
    public String getLabel() {
        return text;
    }
}
