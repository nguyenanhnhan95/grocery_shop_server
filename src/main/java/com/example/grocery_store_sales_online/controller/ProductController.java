package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.projection.ProductProjection;
import com.example.grocery_store_sales_online.service.product.IProductService;
import com.example.grocery_store_sales_online.service.product.impl.ProductService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static com.example.grocery_store_sales_online.utils.CommonConstants.NOT_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    @GetMapping("/search")
    public ResponseEntity<?> findProduct(@RequestParam("query") String queryParameter){
        QueryListResult<ProductProjection> products = productService.getListResult(queryParameter);
        ApiResponse<QueryListResult<ProductProjection>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), products);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping(value = "",consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveModel( @Nullable @RequestPart("product") List<MultipartFile> file,
                                        @Nullable @RequestPart("productItem") List<MultipartFile> files,
                                       @RequestPart("productDto") @Valid ProductDto productDto) {
        productService.preProcessSave(productDto,file,files);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.SAVE_SUCCESS.getCode(), EResponseStatus.SAVE_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }
}
