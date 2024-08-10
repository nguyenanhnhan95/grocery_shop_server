package com.example.grocery_store_sales_online.service.authenticate;

import com.example.grocery_store_sales_online.payload.LoginRequest;

public interface IAuthenticateService {
    String login(LoginRequest loginRequest);
    String refreshToken(String token);

}
