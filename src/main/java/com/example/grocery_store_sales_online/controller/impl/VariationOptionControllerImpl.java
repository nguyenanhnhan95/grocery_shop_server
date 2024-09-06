package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IVariationOptionController;
import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.service.IVariationService;
import com.example.grocery_store_sales_online.service.IVariationOptionService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VariationOptionControllerImpl implements IVariationOptionController {
    private final IVariationOptionService variationOptionService;

    private final IVariationService variationService;

    @Override
    public ResponseEntity<ApiResponse<QueryListResult<VariationOption>>> getListResult(@RequestParam("query") String queryParameter) {
        QueryListResult<VariationOption> variationOptions = variationOptionService.getListResult(queryParameter);
        ApiResponse<QueryListResult<VariationOption>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),
                variationOptions);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<List<VariationOption>>> findAll() {
        List<VariationOption> variationOptions = variationOptionService.findAllAble();
        ApiResponse<List<VariationOption>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),
                variationOptions);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveModel(@Valid @RequestBody VariationOptionDto variationOptionDto) {
        variationOptionService.saveModelDto(variationOptionDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.SAVE_SUCCESS.getCode(), EResponseStatus.SAVE_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<?> editModel(@PathParam("id") Long id, @Valid @RequestBody VariationOptionDto variationOptionDto) {
        variationOptionService.updateModelDto(id, variationOptionDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.EDIT_SUCCESS.getCode(), EResponseStatus.EDIT_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<?> deleteModel(@RequestParam("id") Long id) {
        variationOptionService.deleteModel(id);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.DELETE_SUCCESS.getCode(), EResponseStatus.DELETE_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByIdModel(@RequestParam("id") Long id) {
        Optional<VariationOption> variationOption = variationOptionService.findById(id);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), variationOption);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
