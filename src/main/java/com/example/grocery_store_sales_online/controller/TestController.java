package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.enums.ErrorCode;
import com.example.grocery_store_sales_online.exception.ActiveException;
import com.example.grocery_store_sales_online.model.TestEntity;
import com.example.grocery_store_sales_online.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;
    @PostMapping
    public ResponseEntity<?> saveTestData(@RequestBody TestEntity testEntity){
        if (testService.saveTest(testEntity)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping
    public ResponseEntity<?> test(){
        throw new ActiveException(ErrorCode.ACCOUNT_NOT_ACTIVE);
    }
}
