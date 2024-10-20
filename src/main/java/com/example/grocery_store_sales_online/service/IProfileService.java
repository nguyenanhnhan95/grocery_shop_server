package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.payload.UserCurrent;
import com.example.grocery_store_sales_online.payload.UserScreeThemeOnly;
import com.example.grocery_store_sales_online.security.UserPrincipal;

import java.util.List;
import java.util.Map;

public interface IProfileService {
//    UserCurrent getCurrentProfile(UserPrincipal userPrincipal);


    List<Map<String, String>> listAccountStatus();

}
