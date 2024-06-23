package com.example.grocery_store_sales_online.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    @Size(min = 5,message = "Tài khoản có độ dài ít năm ký tự")
    private String name;
    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 6, message = "Password phải từ 6 kí tự trở lên")
    private String password;
    private boolean flagKeep;
}
