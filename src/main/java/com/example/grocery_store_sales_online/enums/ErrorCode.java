package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

public enum ErrorCode implements IEnumComboItem {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error"),
    USER_EXISTED(1001,"User existed"),
    MAIN_MENU_NOT_FOUND(1002,"menu main no found"),
    ACCOUNT_NOT_ACTIVE(4001,"Account not active"),
    USER_NOT_FOUND(4002,"User not found"),
    VIOLATION_CONSTRAIN(4003,"Validation error"),
    BAD_CREDENTIAL(4004,"Sai tài khoản hoặc mật khẩu "),
    INVALID_TOKEN(4005,"Invalid Token"),
    UNAUTHENTICATED(4006,"Tài khoản chưa được xác thực"),
    EXPIRED_TOKEN(4007,"Token đã hết hạn"),
    REFRESH_TOKEN(4008,"Refresh token"),
    Method_Argument_MVC(4009,"Lối truyền tham số ");
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return message;
    }
}
