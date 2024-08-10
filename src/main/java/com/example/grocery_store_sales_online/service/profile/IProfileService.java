package com.example.grocery_store_sales_online.service.profile;

import com.example.grocery_store_sales_online.payload.Profile;
import com.example.grocery_store_sales_online.security.UserPrincipal;

import java.util.Optional;

public interface IProfileService {
    Profile getCurrentProfile(UserPrincipal userPrincipal);
    void changeScreenMode(Profile profile);
}
