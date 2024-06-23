package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum EPayment implements IEnumComboItem {

    Pending("Đang chờ"),
    Authorized("Được ủy quyền"),
    Partially("Thanh toán một phần"),
    Paid("Đã thanh toán"),
    PartiallyRefunded("Đã hoàn lại một phần"),
    Refunded("Đã hoàn tiền"),
    Processing("Đang xử lý"),
    Voided("Vô hiệu hóa");

     EPayment(String text) {
        this.text = text;
    }

    private String text;
    @Override
    public String getLabel() {
        return text;
    }
}
