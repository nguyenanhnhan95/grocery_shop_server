package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IProfileController;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.payload.UserScreeThemeOnly;
import com.example.grocery_store_sales_online.service.IProfileService;
import com.example.grocery_store_sales_online.service.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileControllerImpl implements IProfileController {
    private final IProfileService profileService;
    private final IUserService userService;
//    @Override
//    public ResponseEntity<?> getCurrenProfile(@CurrentUser UserPrincipal userPrincipal) {
//        ApiResponse<UserCurrent> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), profileService.getCurrentProfile(userPrincipal));
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
    @Override
    public ResponseEntity<?> changeScreenModel(HttpServletResponse response, Long id, UserScreeThemeOnly userScreeThemeOnly){
        userService.changeScreenMode(response,id,userScreeThemeOnly);
        ApiResponse<?> result = new ApiResponse<>(EResponseStatus.EDIT_SUCCESS.getCode(), EResponseStatus.EDIT_SUCCESS.getLabel());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getAccountStatus() {
        ApiResponse<?> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), profileService.listAccountStatus());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
