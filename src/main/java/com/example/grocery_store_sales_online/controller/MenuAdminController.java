package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.components.MainMenu;
import com.example.grocery_store_sales_online.config.MenuAdminProperties;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu/admin-side")
@RequiredArgsConstructor
public class MenuAdminController {
    private final MenuAdminProperties menuAdminProperties;
    @GetMapping
    public ResponseEntity<?> getListMainMenus(@CurrentUser UserPrincipal userPrincipal){
        List<MainMenu> mainMenus = menuAdminProperties.getMainMenus(userPrincipal);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),mainMenus);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/path")
    public ResponseEntity<?> getMainMenuByPath(@CurrentUser UserPrincipal userPrincipal, @RequestParam("path") String path){
        MainMenu menu = menuAdminProperties.getMainMenuPath(userPrincipal,path);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),menu);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/path-children")
    public ResponseEntity<?> getMenuParentByPathChildren(@CurrentUser UserPrincipal userPrincipal, @RequestParam("pathChildren") String pathChildren){
        MainMenu menu = menuAdminProperties.getMenuParentByPathChildren(userPrincipal,pathChildren);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),menu);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
