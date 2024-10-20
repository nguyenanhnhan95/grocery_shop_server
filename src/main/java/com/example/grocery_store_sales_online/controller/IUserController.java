package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface IUserController {

    @GetMapping("/info-user")
    ResponseEntity<?> getInformationUser(@CurrentUser UserPrincipal userPrincipal);
    @GetMapping("/mode-theme")
    ResponseEntity<?> getModeTheme(@CurrentUser UserPrincipal userPrincipal);
}
