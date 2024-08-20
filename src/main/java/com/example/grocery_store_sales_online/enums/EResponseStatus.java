package com.example.grocery_store_sales_online.enums;

import com.example.grocery_store_sales_online.components.IEnumComboItem;

import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FILED_DATA_NOT_EXIST;

public enum EResponseStatus implements IEnumComboItem {
    FETCH_DATA_SUCCESS(200, "Lấy dữ liệu thành công"),
    REFRESH_TOKEN_SUCCESS(200, "Refresh token thành công"),
    SAVE_SUCCESS(200, "Đã lưu dữ liệu thành công"),
    EDIT_SUCCESS(200, "Đã cập nhập dữ liệu thành công"),
    DELETE_SUCCESS(200, "Đã xóa dự liệu thành công"),
    LOGIN_SUCCESS(200, "Đăng nhập thành công "),
    LOGOUT_SUCCESS(200, "log out thành công"),
    ACCESS_USER_SUCCESS(200, "Truy cập thông tin tài khoản thành công ."),

    FETCH_DATA_FAIL(4000, "Lỗi lấy dữ liệu"),
    FETCH_NO_CONTENT(204, "Dữ liệu hện tại trống"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    USER_EXISTED(1001, "User existed"),
    MAIN_MENU_NOT_FOUND(1002, "menu main no found"),
    ACCOUNT_NOT_ACTIVE(4001, "Account not active"),
    USER_NOT_FOUND(4002, "User not found"),
    VIOLATION_CONSTRAIN(4003, "Validation error"),
    BAD_CREDENTIAL(4004, "Sai tài khoản hoặc mật khẩu "),
    INVALID_TOKEN(4005, "Invalid Token"),
    UNAUTHENTICATED(4006, "Tài khoản chưa được xác thực"),
    EXPIRED_TOKEN(4007, "Token đã hết hạn"),
    REFRESH_TOKEN(4008, "Refresh token"),
    METHOD_ARGUMENT_MVC(4009, "Lối truyền tham số "),
    READABLE_FROM_HTTP(4010, "không thể đọc hoặc chuyển đổi thông điệp HTTP thành đối tượng Java "),
    NOT_FOUND_BY_ID(4011, "ID không tồn tại "),
    INTERNAL_SERVER_ERROR(4012, "Illegal xảy ra lỗi"),
    ENTER_DATA_FAIL(4013, "Lỗi nhập dữ lệu"),
    SAVE_FAIL(4014, "Lỗi lưu dữ liệu"),
    EDIT_FAIL(4015, "Lỗi cập nhập dữ liệu"),
    EXISTING(4016, "Dữ liệu đã tồn tại"),
    NO_EXISTING(4017, "Dữ liệu đã không tồn tại"),

    DELETE_FAIL(4018, "Lỗi xóa dữ liệu "),

    LOGOUT_FAIL(4019, "log out không thành công"),
    READ_JSON_RESOURCE(4020, "Lỗi đọc dữ liệu json từ resource"),
    READ_PATH_FILE(4021, "Lỗi truy cập đường UI lỗi"),
    CONFIG_AWS_FILE(4050, "Lỗi config aws "),
    AWS_UPLOAD_IMAGE_FAIL(4051, "Lỗi upload ảnh"),
    AWS_LOAD_IMAGE_FAIL(4052, "Lối load ảnh"),
    AWS_DELETE_OBJECT_FAIL(4053, "Lỗi xóa file trên aws"),
    AWS_COPY_OBJECT(4054, "Lỗi copy file trên aws"),
    FILE_DIRECTORY_FAIL(4101, "Đường dẫn file lưu lỗi"),
    FILE_UP_TO_SERVER_FAIL(4102, "Tải file lên server lỗi"),
    AWS_FILE_LOAD_FAIL(4103, "Tải file  lỗi"),
    AWS_FILE_UPLOAD_FAIL(4104, "Tải file  lỗi"),
    CHANGE_SCREEN_LIGHT_FAIL(4106, "Thay đổi độ sáng màng hình bị lỗi "),
    NOT_PERMISSION_PAGE(4107, "Bạn không có quyền truy cập trang này !"),
    NOT_FOUND_PAGE(4108, "Trang truy cập của bạn không tồn tại !"),
    LOGIN_FAIL(4109, "Đăng nhập không thành công !"),
    ACCOUNT_NOT_EXISTING(4110, "Tài khoản hoặc mật khẩu không đúng !"),
    ACCOUNT_DISABLE(4111, "Tài khoản đã bị vô hiệu hóa !"),
    SIGNATURE_TOKEN_FAIL(4112, "Mã xác thực chữ ký token không đúng !"),
    TOKEN_NOT_SUPPORT(4113, "Mã xác thực token không phù hợp !"),
    TOKEN_NOT_VALIDATE(4114, "Mã token không phù hợp !"),
    TOKEN_NOT_EMPTY(4115, "Mã token trống !"),
    REFRESH_TOKEN_FAIL(4116, "Refresh token lỗi !"),
    NOT_FOUND_USER(4117, "Thông tin tài khoản không tồn tại !"),
    NOT_FOUND_DATA(4118, THIS_FILED_DATA_NOT_EXIST),
    NOT_FORMAT_OBJECT(4119, "Dữ liệu đối tượng không phù hợp ."),
    CONFIG_IMAGE_FAIL(4120, "Cấu hình file ảnh lỗi ."),
    SEND_TO_IMAGE_SERVER_FAIL(4121, "Đẩy ảnh lên server bị lỗi ."),
    CREATE_TOKEN_FAIL(4122, "Lỗi tạo token .");

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
