package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.dto.shop.PromotionDto;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/shop-promotion")
public interface IPromotionController {
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<Promotion>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("")
    ResponseEntity<ApiResponse<List<Promotion>>> findAll();
    @PostMapping("")
    ResponseEntity<?> saveModel(@Valid @RequestBody PromotionDto promotionDto);
    @PatchMapping("/")
    ResponseEntity<?> editModel(@PathParam("id") Long id, @Valid @RequestBody PromotionDto promotionDto);
    @DeleteMapping("/")
    ResponseEntity<?> deleteModel(@RequestParam("id") Long id);
    @GetMapping("/")
    ResponseEntity<?> findByIdModel(@RequestParam("id") Long id);
    @GetMapping("/codes")
    public ResponseEntity<ApiResponse<List<Promotion>>> getListCode();
}
