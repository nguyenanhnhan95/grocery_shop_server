package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IProductController;
import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.dto.product.ProductEditDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.service.IProductItemService;
import com.example.grocery_store_sales_online.service.IProductService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements IProductController {
    private final IProductService productService;
    private final IProductItemService productItemService;
    @Override
    public ResponseEntity<?> findProduct(String queryParameter){
        QueryListResult<ProductProjection> products = productService.getListResult(queryParameter);
        ApiResponse<QueryListResult<ProductProjection>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), products);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> saveModel(
                                        HttpServletRequest request,
                                       ProductDto productDto) {


        productService.saveProductDto(productDto,request);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.SAVE_SUCCESS.getCode(), EResponseStatus.SAVE_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<?> editModel(Long id, HttpServletRequest request, ProductEditDto productEditDto) {
        productService.editProductDto(id,productEditDto,request);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.EDIT_SUCCESS.getCode(), EResponseStatus.EDIT_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }


    @Override
    public ResponseEntity<?> getListResultManage(String queryParameter) {
        return new ResponseEntity<>(new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),productItemService.getListResultManage(queryParameter)),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByIdFormEditModel(Long id) {
        return new ResponseEntity<>(new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),productService.findByIdProductEdit(id)),HttpStatus.OK);
    }
}
