package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.payload.UserCurrent;
import com.example.grocery_store_sales_online.payload.UserScreeThemeOnly;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.common.*;

import java.util.Optional;

public interface IUserService extends IFindByIdAble<User,Long>, ISaveModelAble<User>, IChangeScreenModeAble<Long, UserScreeThemeOnly>,
        IFindByEmailAble<User>,IFindByNameLoginAble<User>,IFindAllAble<User> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    UserCurrent getInformationUserCurrent(UserPrincipal userPrincipal);
    String getModeTheme(UserPrincipal userPrincipal);
}
