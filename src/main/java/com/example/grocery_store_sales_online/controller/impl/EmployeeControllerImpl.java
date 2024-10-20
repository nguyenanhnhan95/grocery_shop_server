package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.controller.IEmployeeController;
import com.example.grocery_store_sales_online.dto.person.EmployeeDto;
import com.example.grocery_store_sales_online.dto.person.EmployeeEditDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.service.IEmployeeService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements IEmployeeController {
    private final IEmployeeService employeeService;
    @Override
    public ResponseEntity<ApiResponse<QueryListResult<EmployeeProjection>>> getListResult(String queryParameter) {
        ApiResponse<QueryListResult<EmployeeProjection>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),
                EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), employeeService.getListResult(queryParameter));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByIdModel(Long id) {
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), employeeService.findByIdProjection(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveModel(EmployeeDto employeeDto, MultipartFile avatar) {
        employeeDto.setAvatar(avatar);
        employeeService.saveModelDto(employeeDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.SAVE_SUCCESS.getCode(), EResponseStatus.SAVE_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<?> editModel(Long id, EmployeeEditDto employeeEditDto, MultipartFile avatar) {
        if(avatar!=null){
            employeeEditDto.setAvatar(avatar);
        }
        employeeService.updateModelDto(id, employeeEditDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.EDIT_SUCCESS.getCode(), EResponseStatus.EDIT_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

    @Override
    public ResponseEntity<?> deleteModel(Long id) {
        employeeService.deleteModel(id);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.DELETE_SUCCESS.getCode(), EResponseStatus.DELETE_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }

//    @GetMapping("/me")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public Employee getCurrentEmployee(@CurrentUser UserPrincipal userPrincipal){
//        return employeeService.findById(userPrincipal.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId(), EResponseStatus.USER_NOT_FOUND));
//    }

}
