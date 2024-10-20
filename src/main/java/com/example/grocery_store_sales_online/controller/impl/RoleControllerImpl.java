package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.components.Permission;
import com.example.grocery_store_sales_online.config.AuthorizationProperties;
import com.example.grocery_store_sales_online.controller.IRoleController;
import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.dto.person.RoleEditDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.service.IRoleService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements IRoleController {
    private final IRoleService roleService;
    private final AuthorizationProperties authorizationProperties;
    @Override
    public ResponseEntity<ApiResponse<QueryListResult<Role>>> getListResult(String queryParameter) {
        ApiResponse<QueryListResult<Role>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),
                EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), roleService.getListResult(queryParameter));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ApiResponse<?>> getList() {
        ApiResponse<?> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),
                EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), roleService.findAllAble());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getListAlias() {
        ApiResponse<?> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),
                EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), roleService.findListAlias());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getListRoleByERole(String name) {
        ApiResponse<?> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),
                EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), roleService.listNameRoleByERole(name));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<ApiResponse<?>> getListRoleEmployee() {
        ApiResponse<?> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),
                EResponseStatus.FETCH_DATA_SUCCESS.getLabel(), roleService.listRoleEmployee());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> deleteModel(Long id) {
        roleService.deleteModel(id);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.DELETE_SUCCESS.getCode(), EResponseStatus.DELETE_SUCCESS.getLabel());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ApiResponse<Set<Permission>>> getListResult() {
        ApiResponse<Set<Permission>> result = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(),
                EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),authorizationProperties.getPermissions() );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> findByIdModel(Long id) {
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),roleService.findById(id) );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> saveModel(RoleDto roleDto) {
        roleService.saveModelDto(roleDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.SAVE_SUCCESS.getCode(), EResponseStatus.SAVE_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }
    @Override
    public ResponseEntity<?> editModel(Long id, RoleEditDto roleEditDto) {
        roleService.updateModelDto(id, roleEditDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.EDIT_SUCCESS.getCode(), EResponseStatus.EDIT_SUCCESS.getLabel());
        return ResponseEntity.ok().body(apiResponse);
    }
}
