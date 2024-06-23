package com.example.grocery_store_sales_online.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String tokenType="Bearer";


    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
