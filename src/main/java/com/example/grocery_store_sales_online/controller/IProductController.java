package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.dto.product.ProductDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.List;

@RequestMapping("/products")
public interface IProductController {
    @GetMapping("/search")
    ResponseEntity<?> findProduct(@RequestParam("query") String queryParameter);

    @PostMapping(value = "", consumes = {"multipart/form-data"})
    ResponseEntity<?> saveModel(@Nullable @RequestPart("product") List<MultipartFile> file,
                                @Nullable @RequestPart("productItem") List<MultipartFile> files,
                                @RequestPart("productDto") @Valid ProductDto productDto);
}