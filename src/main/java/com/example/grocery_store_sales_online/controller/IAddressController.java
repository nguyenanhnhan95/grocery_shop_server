package com.example.grocery_store_sales_online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/address")
public interface IAddressController {
    @GetMapping("/provinces")
    ResponseEntity<?> getListProvince();
    @GetMapping("/province/")
    ResponseEntity<?> getByCodeProvince(@RequestParam("code") String code);
    @GetMapping("/district/")
    ResponseEntity<?> getListDistrictByCodeProvince(@RequestParam("code") String code);
    @GetMapping("/ward/")
    public ResponseEntity<?> getListWardByCodeDistrict(@RequestParam("code") String code);
}
