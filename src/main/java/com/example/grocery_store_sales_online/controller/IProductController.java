package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.custom.validation.FileSizeConstraint;
import com.example.grocery_store_sales_online.custom.validation.FileTypeConstraint;
import com.example.grocery_store_sales_online.custom.validation.ListNotEmptyConstraint;
import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.dto.product.ProductEditDto;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.List;

@RequestMapping("/products")
public interface IProductController {
    @PreAuthorize("@requiredPermission.checkPermission('products:view')")
    @GetMapping("/search")
    ResponseEntity<?> findProduct(@RequestParam("query") String queryParameter);
    @PreAuthorize("@requiredPermission.checkPermission('products:add')")
    @PostMapping(value = "", consumes = {"multipart/form-data"})
    ResponseEntity<?> saveModel(
            HttpServletRequest request,
            @RequestPart("productDto")  @Valid ProductDto productDto);
    @PreAuthorize("@requiredPermission.checkPermission('products:edit')")
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    ResponseEntity<?> editModel(@PathVariable("id") Long id,
            HttpServletRequest request,
            @RequestPart("productEditDto")  @Valid ProductEditDto productEditDto);
    @GetMapping("/manage/search")
    ResponseEntity<?> getListResultManage(@RequestParam("query") String queryParameter);
    @GetMapping("/form-edit/{id}")
    ResponseEntity<?> findByIdFormEditModel(@PathVariable("id") Long id);
}