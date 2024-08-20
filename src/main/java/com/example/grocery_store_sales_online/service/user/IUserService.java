package com.example.grocery_store_sales_online.service.user;

import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.service.common.*;

import java.util.Optional;

public interface IUserService extends IFindByIdAble<User,Long>, ISaveModelAble<User>, IChangeScreenModeAble<Long, EScreenTheme>, IFindByEmailAble<User> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

}
