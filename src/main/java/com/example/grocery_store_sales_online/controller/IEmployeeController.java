package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.custom.validation.FileNotEmptyConstraint;
import com.example.grocery_store_sales_online.custom.validation.FileSizeConstraint;
import com.example.grocery_store_sales_online.custom.validation.FileTypeConstraint;
import com.example.grocery_store_sales_online.dto.person.EmployeeDto;
import com.example.grocery_store_sales_online.dto.person.EmployeeEditDto;
import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

@RequestMapping("/employee")
public interface IEmployeeController {
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<EmployeeProjection>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("/")
    ResponseEntity<?> findByIdModel(@RequestParam("id") Long id);
    @Validated
    @PostMapping(value = "",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<?> saveModel(@RequestPart("employeeDto") @Valid  EmployeeDto employeeDto,
                                 @RequestPart("avatar")  @FileSizeConstraint(maxSize = CommonConstants.MAX_FILE_SIZE_IMAGE)
                                 @FileTypeConstraint(contentType = {"image/png", "image/jpg", "image/jpeg"}) @Nullable  MultipartFile avatar);
    @Validated
    @PatchMapping("/")
    ResponseEntity<?> editModel(@RequestParam("id") Long id, @RequestPart("employeeDto") @Valid EmployeeEditDto employeeEditDto,
                                @RequestPart("avatar")  @FileSizeConstraint(maxSize = CommonConstants.MAX_FILE_SIZE_IMAGE)
                                @FileTypeConstraint(contentType = {"image/png", "image/jpg", "image/jpeg"}) @Nullable  MultipartFile avatar);
}
