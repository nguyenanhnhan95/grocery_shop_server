package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum EEmployeeRole implements IEnumComboItem{
    EMPLOYEE_SECURITY("Nhân viên giám sát"),
    EMPLOYEE_TRANSPORTATION("Nhân viên vận chuyển"),
    EMPLOYEE_INSPECTION_GOODS("Nhân viên kiểm tra hàng hóa"),
    EMPLOYEE_SALES("Nhân viên bán hàng"),
    EMPLOYEE_STATISTIC("Nhân viên thông kê");

    EEmployeeRole(String label) {
        this.label = label;
    }


    private final String label;


    @Override
    public String getLabel() {
        return label;
    }
}
