package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.security.CurrentUser;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/menu/admin-side")
public interface IMenuAdminController {
    @GetMapping
    ResponseEntity<?> getListMainMenus(@CurrentUser UserPrincipal userPrincipal);
    @GetMapping("/path")
    ResponseEntity<?> getMainMenuByPath(@CurrentUser UserPrincipal userPrincipal, @RequestParam("path") String path);
    @GetMapping("/path-children")
    ResponseEntity<?> getMenuParentByPathChildren(@CurrentUser UserPrincipal userPrincipal, @RequestParam("pathChildren") String pathChildren);
}
