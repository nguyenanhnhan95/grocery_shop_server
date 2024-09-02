package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.components.Permission;
import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/role")
public interface IRoleController {
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<Role>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("/alias")
    ResponseEntity<ApiResponse<?>> getList();
    @GetMapping("/employee")
    ResponseEntity<ApiResponse<?>> getListRoleEmployee();
    @DeleteMapping("/")
    ResponseEntity<?> deleteModel(@RequestParam("id") Long id);
    @GetMapping("/permissions")
    ResponseEntity<ApiResponse<Set<Permission>>> getListResult();
    @GetMapping("/")
    ResponseEntity<?> findByIdModel(@RequestParam("id") Long id);
    @PostMapping("")
    ResponseEntity<?> saveModel(@Valid @RequestBody RoleDto roleDto);
    @PatchMapping("/")
    ResponseEntity<?> editModel(@PathParam("id") Long id, @Valid @RequestBody RoleDto roleDto);
}
