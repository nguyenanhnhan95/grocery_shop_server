package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.payload.LoginRequest;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IAuthenticateService {
    String login(LoginRequest loginRequest,HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
    List<String> getRoleAuthorize(UserPrincipal userPrincipal);

    void checkAuthenticated();
    void authorizePage(String permission);
}
