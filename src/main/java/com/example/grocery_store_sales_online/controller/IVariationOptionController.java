package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products-variation-option")
public interface IVariationOptionController {
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<VariationOption>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("")
    ResponseEntity<ApiResponse<List<VariationOption>>> findAll();
    @PostMapping("")
    ResponseEntity<?> saveModel(@Valid @RequestBody VariationOptionDto variationOptionDto);
    @PatchMapping("/")
    ResponseEntity<?> editModel(@PathParam("id") Long id, @Valid @RequestBody VariationOptionDto variationOptionDto);
    @DeleteMapping("/")
    ResponseEntity<?> deleteModel(@RequestParam("id") Long id);
    @GetMapping("/")
    ResponseEntity<?> findByIdModel(@RequestParam("id") Long id);
}
