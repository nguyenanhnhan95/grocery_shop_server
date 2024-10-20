package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IUserController;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.payload.UserCurrent;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements IUserController {
    private final IUserService userService;
    @Override
    public ResponseEntity<?> getInformationUser(UserPrincipal userPrincipal) {
        ApiResponse<UserCurrent> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), userService.getInformationUserCurrent(userPrincipal));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getModeTheme(UserPrincipal userPrincipal) {
        ApiResponse<?> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), userService.getModeTheme(userPrincipal));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
