package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IAuthController;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.AppException;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.payload.LoginRequest;
import com.example.grocery_store_sales_online.payload.SignUpRequest;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.IAuthenticateService;
import com.example.grocery_store_sales_online.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements IAuthController {
    private final IAuthenticateService authenticateService;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest,HttpServletRequest request, HttpServletResponse response) {
        authenticateService.login(loginRequest,request,response);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.LOGIN_SUCCESS.getCode(),EResponseStatus.LOGIN_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authenticateService.logout(request,response);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.LOGOUT_SUCCESS.getCode(),EResponseStatus.LOGOUT_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> checkAuthenticate() {
        authenticateService.checkAuthenticated();
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.AUTHENTICATE_SUCCESS.getCode(),EResponseStatus.AUTHENTICATE_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> authorizePage(String requirePage) {
        authenticateService.authorizePage(requirePage);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.AUTHENTICATE_SUCCESS.getCode(),EResponseStatus.AUTHENTICATE_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getRolesUser(UserPrincipal userPrincipal){
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),authenticateService.getRoleAuthorize(userPrincipal));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
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
