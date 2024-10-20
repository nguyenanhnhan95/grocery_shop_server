package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum ECustomerRole implements IEnumComboItem {
    CUSTOMER_NEW("Khách hàng mới"),
    CUSTOMER_REGULAR("Khách hàng thường xuyên"),
    CUSTOMER_POTENTIAL("Khách hàng tiềm năng"),
    CUSTOMER_VIP("Khách hàng vip"),
    CUSTOMER_BLACK("Khách hàng rủi ro");

    ECustomerRole(String label) {
        this.label = label;
    }

    private String label;

    @Override
    public String getLabel() {
        return label;
    }
}
