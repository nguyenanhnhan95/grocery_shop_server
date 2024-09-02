package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.dto.product.VariationDto;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products-variation")
public interface IVariationController {
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<Variation>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("")
    ResponseEntity<ApiResponse<List<Variation>>> findAll();
    @PostMapping("")
    ResponseEntity<?> saveModel(@Valid @RequestBody VariationDto variationDto);
    @PatchMapping("/")
    ResponseEntity<?> editModel(@PathParam("id") Long id, @Valid @RequestBody VariationDto variationDto);
    @DeleteMapping("/")
    ResponseEntity<?> deleteModel(@RequestParam("id") Long id);
    @GetMapping("/")
    ResponseEntity<?> findByIdModel(@RequestParam("id") Long id);
}
