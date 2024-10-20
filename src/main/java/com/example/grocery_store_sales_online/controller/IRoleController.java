package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.components.Permission;
import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.dto.person.RoleEditDto;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/role")
public interface IRoleController {
    @PreAuthorize("@requiredPermission.checkPermission('role:view')")
    @GetMapping("/search")
    ResponseEntity<ApiResponse<QueryListResult<Role>>> getListResult(@RequestParam("query") String queryParameter);
    @GetMapping("")
    ResponseEntity<ApiResponse<?>> getList();
    @GetMapping("/alias")
    ResponseEntity<ApiResponse<?>> getListAlias();
    @GetMapping("/e-role")
    ResponseEntity<ApiResponse<?>> getListRoleByERole(@RequestParam("name") String name);
    @GetMapping("/employee")
    ResponseEntity<ApiResponse<?>> getListRoleEmployee();
    @PreAuthorize("@requiredPermission.checkPermission('role:delete')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteModel(@PathVariable("id") Long id);
    @GetMapping("/permissions")
    ResponseEntity<ApiResponse<Set<Permission>>> getListResult();
    @GetMapping("/{id}")
    ResponseEntity<?> findByIdModel(@PathVariable("id") Long id);
    @PreAuthorize("@requiredPermission.checkPermission('role:add')")
    @PostMapping("")
    ResponseEntity<?> saveModel(@Valid @RequestBody RoleDto roleDto);
    @PreAuthorize("@requiredPermission.checkPermission('role:edit')")
    @PutMapping("/{id}")
    ResponseEntity<?> editModel(@PathVariable("id") Long id, @Valid @RequestBody RoleEditDto roleEditDto);
}
