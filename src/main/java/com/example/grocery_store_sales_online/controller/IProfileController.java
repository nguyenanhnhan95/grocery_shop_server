package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.payload.Profile;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/profile")
public interface IProfileController {
    @GetMapping()
    ResponseEntity<?> getCurrenProfile(@CurrentUser UserPrincipal userPrincipal);
    @PatchMapping("/change-dark")
    ResponseEntity<?> changeScreenModel(@RequestBody Profile profile);
    @GetMapping("/account-status")
    public ResponseEntity<?> getAccountStatus();
}
