package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.components.MainMenu;
import com.example.grocery_store_sales_online.config.MenuAdminProperties;
import com.example.grocery_store_sales_online.enums.ErrorCode;
import com.example.grocery_store_sales_online.exception.ResourceNotFoundException;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuCategory {
    private final MenuAdminProperties menuAdminProperties;
    @GetMapping("/admin-side")
    public ResponseEntity<List<MainMenu>> getListMainMenus(@CurrentUser UserPrincipal userPrincipal){
        List<MainMenu> mainMenus = menuAdminProperties.getMainMenus(userPrincipal);
            return new ResponseEntity<>(mainMenus, HttpStatus.OK);
    }
}
