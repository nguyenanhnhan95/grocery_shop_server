package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.components.MainMenu;
import com.example.grocery_store_sales_online.config.MenuAdminProperties;
import com.example.grocery_store_sales_online.controller.IMenuAdminController;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuAdminControllerImpl implements IMenuAdminController {
    private final MenuAdminProperties menuAdminProperties;
    @Override
    public ResponseEntity<?> getListMainMenus(UserPrincipal userPrincipal){
        List<MainMenu> mainMenus = menuAdminProperties.getMainMenus(userPrincipal);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),mainMenus);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getMainMenuByPath(UserPrincipal userPrincipal, String path){
        MainMenu menu = menuAdminProperties.getMainMenuPath(userPrincipal,path);
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),menu);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getMenuParentByPathChildren(UserPrincipal userPrincipal, String pathChildren){
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.FETCH_DATA_SUCCESS.getCode(), EResponseStatus.FETCH_DATA_SUCCESS.getLabel(),menuAdminProperties.getMenuParentByPathChildren(userPrincipal,pathChildren));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
