package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IProductCategoryController;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductCategoryControllerImpl implements IProductCategoryController {
    private final IProductCategoryService productCategoryService;

    @Override
    public ResponseEntity<?> getProductCategories(){
        ApiResponse<List<ProductCategory>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), productCategoryService.findAllMenu());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getChildrenProductCategories(){
        ApiResponse<List<ProductCategory>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), productCategoryService.findAllChildren());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
