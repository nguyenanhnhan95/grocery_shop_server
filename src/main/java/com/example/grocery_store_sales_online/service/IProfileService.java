package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.payload.Profile;
import com.example.grocery_store_sales_online.security.UserPrincipal;

import java.util.List;
import java.util.Map;

public interface IProfileService {
    Profile getCurrentProfile(UserPrincipal userPrincipal);
    void changeScreenMode(Profile profile);

    List<Map<String, String>> listAccountStatus();

}
