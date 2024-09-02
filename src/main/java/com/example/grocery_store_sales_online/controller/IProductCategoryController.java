package com.example.grocery_store_sales_online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product-category")
public interface IProductCategoryController {
    @GetMapping()
    ResponseEntity<?> getProductCategories();
    @GetMapping("/children")
    ResponseEntity<?> getChildrenProductCategories();
}
