package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.custom.validation.FileSizeConstraint;
import com.example.grocery_store_sales_online.custom.validation.FileTypeConstraint;
import com.example.grocery_store_sales_online.dto.person.EmployeeDto;
import com.example.grocery_store_sales_online.dto.person.EmployeeEditDto;

import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

@RequestMapping("/employee")
@Validated
public interface IEmployeeController {
    @PreAuthorize("@requiredPermission.checkPermission('employee:view')")
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<EmployeeProjection>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("/{id}")
    ResponseEntity<?> findByIdModel(@PathVariable("id") Long id);

    @PreAuthorize("@requiredPermission.checkPermission('employee:add')")
    @PostMapping(value = "",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> saveModel(@RequestPart("employeeDto") @Valid  EmployeeDto employeeDto,
                                 @RequestPart("avatar")  @FileSizeConstraint(maxSize = CommonConstants.MAX_FILE_SIZE_IMAGE)
                                 @FileTypeConstraint(contentType = {"image/png", "image/jpg", "image/jpeg"}) @Nullable  MultipartFile avatar);
    @PreAuthorize("@requiredPermission.checkPermission('employee:edit')")
    @PutMapping("/{id}")
    ResponseEntity<?> editModel(@PathVariable("id") Long id, @RequestPart("employeeEditDto") @Valid EmployeeEditDto employeeEditDto,
                                @RequestPart("avatar") @Nullable   @FileSizeConstraint(maxSize = CommonConstants.MAX_FILE_SIZE_IMAGE)
                                @FileTypeConstraint(contentType = {"image/png", "image/jpg", "image/jpeg"})  MultipartFile avatar);
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteModel(@PathVariable("id") Long id);
}
