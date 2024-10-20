package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IOptionVariationController;
import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.service.IVariationOptionService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OptionVariationControllerImpl implements IOptionVariationController {
    private final IVariationOptionService variationOptionService;


    @Override
    public ResponseEntity<ApiResponse<QueryListResult<VariationOption>>> getListResult(String queryParameter) {
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
    public ResponseEntity<?> saveModel(VariationOptionDto variationOptionDto) {
        variationOptionService.saveModelDto(variationOptionDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.SAVE_SUCCESS.getCode(), EResponseStatus.SAVE_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<?> editModel(Long id, VariationOptionDto variationOptionDto) {
        variationOptionService.updateModelDto(id, variationOptionDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.EDIT_SUCCESS.getCode(), EResponseStatus.EDIT_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<?> deleteModel(Long id) {
        variationOptionService.deleteModel(id);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.DELETE_SUCCESS.getCode(), EResponseStatus.DELETE_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByIdModel(Long id) {
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), variationOptionService.findByIdProjection(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
