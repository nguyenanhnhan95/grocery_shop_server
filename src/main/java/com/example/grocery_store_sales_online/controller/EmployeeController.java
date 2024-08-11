package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ResourceNotFoundException;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.employee.impl.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class EmployeeController {
    private final EmployeeService employeeService;


    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Employee getCurrentEmployee(@CurrentUser UserPrincipal userPrincipal){
        return employeeService.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId(), EResponseStatus.USER_NOT_FOUND));
    }

}
