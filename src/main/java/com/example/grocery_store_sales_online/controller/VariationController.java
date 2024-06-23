package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.dto.product.VariationDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.service.variation.IVariationService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products-variation")
@RequiredArgsConstructor
public class VariationController {
    private final IVariationService variationService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<QueryListResult<Variation>>> getListResult(@RequestParam("query") String queryParameter) {
        QueryListResult<Variation> variations = variationService.getListResult(queryParameter);
        ApiResponse<QueryListResult<Variation>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), variations);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Variation>>> findAll() {
        List<Variation> variations = variationService.findAll();
        ApiResponse<List<Variation>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), variations);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> saveModel(@Valid @RequestBody VariationDto variationDto) {
        variationService.saveModelDto(variationDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.SAVE_SUCCESS.getCode(), EResponseStatus.SAVE_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

    @PatchMapping("/")
    public ResponseEntity<?> editModel(@PathParam("id") Long id, @Valid @RequestBody VariationDto variationDto) {
        variationService.updateModelDto(id, variationDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.EDIT_SUCCESS.getCode(), EResponseStatus.EDIT_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteModel(@RequestParam("id") Long id) {
        variationService.deleteModel(id);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.DELETE_SUCCESS.getCode(), EResponseStatus.DELETE_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> findByIdModel(@RequestParam("id") Long id) {
        Optional<Variation> variation = variationService.findById(id);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), variation);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}

