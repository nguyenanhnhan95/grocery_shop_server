package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IProfileController;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.payload.Profile;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileControllerImpl implements IProfileController {
    private final IProfileService profileService;
    @Override
    public ResponseEntity<?> getCurrenProfile(@CurrentUser UserPrincipal userPrincipal) {
        ApiResponse<Profile> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), profileService.getCurrentProfile(userPrincipal));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> changeScreenModel(@RequestBody Profile profile){
        profileService.changeScreenMode(profile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getAccountStatus() {
        ApiResponse<?> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), profileService.listAccountStatus());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
