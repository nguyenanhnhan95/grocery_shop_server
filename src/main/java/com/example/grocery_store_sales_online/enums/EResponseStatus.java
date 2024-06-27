package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum EResponseStatus implements IEnumComboItem {
    FETCH_DATA_SUCCESS(200,"Lấy dữ liệu thành công"),
    FETCH_DATA_FAIL(4000,"Lỗi lấy dữ liệu"),
    FETCH_NO_CONTENT(204,"Dữ liệu hện tại trống"),
    SAVE_SUCCESS(200,"Đã lưu dữ liệu thành công"),
    EDIT_SUCCESS(200,"Đã cập nhập dữ liệu thành công"),
    ENTER_DATA_FAIL(4005,"Lỗi nhập dữ lệu"),
    SAVE_FAIL(4001,"Lỗi lưu dữ liệu"),
    EDIT_FAIL(4002,"Lỗi cập nhập dữ liệu"),
    EXISTING(406,"Dữ liệu đã tồn tại"),
    NO_EXISTING(406,"Dữ liệu đã không tồn tại"),
    DELETE_SUCCESS(200,"Đã xóa dự liệu thành công"),
    DELETE_FAIL(400,"Lỗi xóa dữ liệu "),
    LOGOUT_SUCCESS(200,"log out thành công"),
    LOGOUT_FAIL(4006,"log out không thành công"),
    READ_JSON_RESOURCE(4007,"Lỗi đọc dữ liệu json từ resource"),
    READ_PATH_FILE(4008,"Lỗi truy cập đường UI lỗi");


    EResponseStatus(int code, String text) {
        this.code = code;
        this.text = text;
    }

    private final int code;
    private final String text;
    @Override
    public String getLabel() {
        return text;
    }

    public int getCode() {
        return code;
    }
}
