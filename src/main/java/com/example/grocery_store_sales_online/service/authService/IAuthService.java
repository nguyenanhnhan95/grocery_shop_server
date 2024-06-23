package com.example.grocery_store_sales_online.service.authService;

import com.example.grocery_store_sales_online.model.account.Role;
import com.example.grocery_store_sales_online.payload.UserResponse;
import com.example.grocery_store_sales_online.security.UserPrincipal;

import java.util.Optional;

public interface IAuthService {
    Optional<UserResponse> getCurrentUser(UserPrincipal userPrincipal);
}
