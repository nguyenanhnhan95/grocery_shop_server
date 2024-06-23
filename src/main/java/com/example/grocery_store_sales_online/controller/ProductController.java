package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.service.product.ProductService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/view")
    public ResponseEntity<QueryListResult<Product>> findProduct(@RequestParam("query") String queryParameter){
                return new ResponseEntity<>(productService.finAll(queryParameter), HttpStatus.OK);
    }
}
