package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.service.productCategory.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final IProductCategoryService productCategoryService;

    @GetMapping()
    public ResponseEntity<List<ProductCategory>> getProductCategories(){
        return new ResponseEntity<>(productCategoryService.findAllProductCategories(), HttpStatus.OK);
    }
}
