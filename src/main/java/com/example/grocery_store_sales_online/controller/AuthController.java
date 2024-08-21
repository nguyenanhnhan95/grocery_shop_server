package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.AppException;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.payload.LoginRequest;
import com.example.grocery_store_sales_online.payload.SignUpRequest;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.TokenProvider;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.authenticate.IAuthenticateService;
import com.example.grocery_store_sales_online.service.user.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthenticateService authenticateService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        authenticateService.login(loginRequest,response);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.LOGIN_SUCCESS.getCode(),EResponseStatus.LOGIN_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authenticateService.logout(request,response);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.LOGOUT_SUCCESS.getCode(),EResponseStatus.LOGOUT_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthenticate() {
        authenticateService.checkAuthenticated();
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.AUTHENTICATE_SUCCESS.getCode(),EResponseStatus.AUTHENTICATE_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request,HttpServletResponse response){
        authenticateService.refreshToken(request,response);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.REFRESH_TOKEN_SUCCESS.getCode(),EResponseStatus.REFRESH_TOKEN_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/role")
    public ResponseEntity<?> getRolesUser(@CurrentUser UserPrincipal userPrincipal){
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),authenticateService.getRoleAuthorize(userPrincipal));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userService.existsByEmail(signUpRequest.getEmail())) {
            throw new AppException(EResponseStatus.USER_EXISTED);
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userService.saveModel(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(1000, "User registered successfully@"));
    }
}
