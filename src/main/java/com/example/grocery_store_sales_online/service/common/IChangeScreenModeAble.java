package com.example.grocery_store_sales_online.service.common;


import jakarta.servlet.http.HttpServletResponse;

public interface IChangeScreenModeAble <ID,Mode>{
    void changeScreenMode(HttpServletResponse response, ID id, Mode mode);
}
