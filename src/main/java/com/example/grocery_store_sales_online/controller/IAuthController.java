package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.payload.LoginRequest;
import com.example.grocery_store_sales_online.payload.SignUpRequest;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
public interface IAuthController {
    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,HttpServletRequest request, HttpServletResponse response);
    @GetMapping("/logout")
    ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response);
    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthenticate();

    @GetMapping("/authorize-page")
    public ResponseEntity<?> authorizePage(@RequestParam("require-page")String requirePage );
    @GetMapping("/role")
    ResponseEntity<?> getRolesUser(@CurrentUser UserPrincipal userPrincipal);
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest);

}
