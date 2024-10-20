package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.dto.shop.PromotionDto;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/shop-promotion")
public interface IPromotionController {
    @PreAuthorize("@requiredPermission.checkPermission('shop-promotion:view')")
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<Promotion>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("")
    ResponseEntity<ApiResponse<List<Promotion>>> findAll();
    @PreAuthorize("@requiredPermission.checkPermission('shop-promotion:add')")
    @PostMapping("")
    ResponseEntity<?> saveModel(@Valid @RequestBody PromotionDto promotionDto);
    @PreAuthorize("@requiredPermission.checkPermission('shop-promotion:edit')")
    @PutMapping("/{id}")
    ResponseEntity<?> editModel(@PathVariable("id") Long id, @Valid @RequestBody PromotionDto promotionDto);
    @PreAuthorize("@requiredPermission.checkPermission('shop-promotion:delete')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteModel(@PathVariable("id") Long id);
    @GetMapping("/{id}")
    ResponseEntity<?> findByIdModel(@PathVariable("id") Long id);
    @GetMapping("/codes")
    ResponseEntity<ApiResponse<List<Promotion>>> getListCode();
}
