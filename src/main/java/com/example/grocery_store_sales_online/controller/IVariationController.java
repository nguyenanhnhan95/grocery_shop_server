package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.dto.product.VariationDto;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.projection.product.VariationProjection;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/variation")
public interface IVariationController {
    @PreAuthorize("@requiredPermission.checkPermission('variation:view')")
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<VariationProjection>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("")
    ResponseEntity<ApiResponse<List<Variation>>> findAll();
    @PreAuthorize("@requiredPermission.checkPermission('variation:add')")
    @PostMapping("")
    ResponseEntity<?> saveModel(@Valid @RequestBody VariationDto variationDto);
    @PreAuthorize("@requiredPermission.checkPermission('variation:edit')")
    @PutMapping("/{id}")
    ResponseEntity<?> editModel(@PathVariable("id") Long id, @Valid @RequestBody VariationDto variationDto);
    @PreAuthorize("@requiredPermission.checkPermission('variation:delete')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteModel(@PathVariable("id") Long id);
    @GetMapping("/{id}")
    ResponseEntity<?> findByIdModel(@PathVariable("id") Long id);
}
