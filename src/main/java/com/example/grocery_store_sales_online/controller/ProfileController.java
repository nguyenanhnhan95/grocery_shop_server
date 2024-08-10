package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ResourceNotFoundException;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.payload.Profile;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.profile.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final IProfileService authService;
    @GetMapping()
    public ResponseEntity<?> getCurrenProfile(@CurrentUser UserPrincipal userPrincipal) {
        ApiResponse<Profile> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), authService.getCurrentProfile(userPrincipal));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PatchMapping("/change-dark")
    public ResponseEntity<?> changeScreenModel(@RequestBody Profile profile){
        authService.changeScreenMode(profile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
