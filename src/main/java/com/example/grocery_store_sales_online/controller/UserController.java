package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.enums.ErrorCode;
import com.example.grocery_store_sales_online.exception.ResourceNotFoundException;
import com.example.grocery_store_sales_online.model.account.Role;
import com.example.grocery_store_sales_online.payload.UserResponse;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.authService.IAuthService;
import com.example.grocery_store_sales_online.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final IAuthService authService;
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        Optional<UserResponse> userResponse = authService.getCurrentUser(userPrincipal);
        if(userResponse.isPresent()){
            return new ResponseEntity<>(userResponse.get(), HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("User", "id", userPrincipal.getId(), ErrorCode.USER_NOT_FOUND);
        }
    }
}
