package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IAddressController;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.service.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class AddressControllerImpl implements IAddressController {
    private final IAddressService addressService;
    @Override
    public ResponseEntity<?> getListProvince() {
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),addressService.findAllProvinces());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getByCodeProvince(String code) {
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),addressService.findByCodeProvinceProjection(code));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getListDistrictByCodeProvince(String code) {
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),addressService.listDistrictsByCodeProvince(code));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getListWardByCodeDistrict(String code) {
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),addressService.listWardsByCodeDistrict(code));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
