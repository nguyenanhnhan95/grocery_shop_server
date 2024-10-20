package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.payload.UserCurrent;
import com.example.grocery_store_sales_online.payload.UserScreeThemeOnly;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/profile")
public interface IProfileController {
//    @GetMapping()
//    ResponseEntity<?> getCurrenProfile(@CurrentUser UserPrincipal userPrincipal);
    @PatchMapping("/change-dark/{id}")
    ResponseEntity<?> changeScreenModel(HttpServletResponse response, @PathVariable("id") Long id, @RequestBody UserScreeThemeOnly userScreeThemeOnly);
    @GetMapping("/account-status")
    public ResponseEntity<?> getAccountStatus();
}
