package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.components.MainMenu;
import com.example.grocery_store_sales_online.config.MenuAdminProperties;
import com.example.grocery_store_sales_online.enums.ErrorCode;
import com.example.grocery_store_sales_online.exception.ResourceNotFoundException;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("/admin")
public class EmployeeController {
    private final EmployeeService employeeService;

    private final MenuAdminProperties menuAdminProperties;
    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Employee getCurrentEmployee(@CurrentUser UserPrincipal userPrincipal){
        return employeeService.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId(), ErrorCode.USER_NOT_FOUND));
    }
    @GetMapping("/main-menus")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
    public ResponseEntity<List<MainMenu>> getListMainMenus(@CurrentUser UserPrincipal userPrincipal){
        List<MainMenu> mainMenus = menuAdminProperties.getMainMenus(userPrincipal);
        if(mainMenus==null || mainMenus.isEmpty()){
            throw  new  ResourceNotFoundException("main menu","","",ErrorCode.MAIN_MENU_NOT_FOUND);
        }else {
            return new ResponseEntity<>(mainMenus,HttpStatus.OK);
        }
    }
}
