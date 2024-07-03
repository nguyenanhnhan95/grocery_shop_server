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
    READ_PATH_FILE(4008,"Lỗi truy cập đường UI lỗi"),
    CONFIG_AWS_FILE(4008,"Lỗi config aws "),
    AWS_UPLOAD_IMAGE_FAIL(4009,"Lỗi upload ảnh"),
    AWS_LOAD_IMAGE_FAIL(4009,"Lối load ảnh"),
    AWS_DELETE_OBJECT_FAIL(4010,"Lỗi xóa file trên aws"),
    AWS_COPY_OBJECT(4011,"Lỗi copy file trên aws"),
    FILE_DIRECTORY_FAIL(4012,"Đường dẫn file lưu lỗi"),
    FILE_UP_TO_SERVER_FAIL(4013,"Tải file lên server lỗi"),
    AWS_FILE_LOAD_FAIL(4014,"Tải file  lỗi"),
    AWS_FILE_UPLOAD_FAIL(4014,"Tải file  lỗi");


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
