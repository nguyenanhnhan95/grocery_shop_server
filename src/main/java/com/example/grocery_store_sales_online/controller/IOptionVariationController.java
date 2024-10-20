package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/option-variation")
public interface IOptionVariationController {
    @PreAuthorize("@requiredPermission.checkPermission('option-variation:view')")
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<VariationOption>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("")
    ResponseEntity<ApiResponse<List<VariationOption>>> findAll();
    @PreAuthorize("@requiredPermission.checkPermission('option-variation:add')")
    @PostMapping("")
    ResponseEntity<?> saveModel(@Valid @RequestBody VariationOptionDto variationOptionDto);
    @PreAuthorize("@requiredPermission.checkPermission('option-variation:edit')")
    @PutMapping("/{id}")
    ResponseEntity<?> editModel(@PathVariable("id") Long id, @Valid @RequestBody VariationOptionDto variationOptionDto);
    @PreAuthorize("@requiredPermission.checkPermission('option-variation:delete')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteModel(@PathVariable("id") Long id);
    @GetMapping("/{id}")
    ResponseEntity<?> findByIdModel(@PathVariable("id") Long id);

}
