package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IProductController;
import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.service.IProductService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements IProductController {
    private final IProductService productService;
    @Override
    public ResponseEntity<?> findProduct(@RequestParam("query") String queryParameter){
        QueryListResult<ProductProjection> products = productService.getListResult(queryParameter);
        ApiResponse<QueryListResult<ProductProjection>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), products);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> saveModel( @Nullable @RequestPart("product") List<MultipartFile> file,
                                        @Nullable @RequestPart("productItem") List<MultipartFile> files,
                                       @RequestPart("productDto") @Valid ProductDto productDto) {
        productService.preProcessSave(productDto,file,files);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.SAVE_SUCCESS.getCode(), EResponseStatus.SAVE_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }
}
