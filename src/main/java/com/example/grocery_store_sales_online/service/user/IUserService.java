package com.example.grocery_store_sales_online.service.user;

import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.service.common.IChangeScreenModeAble;
import com.example.grocery_store_sales_online.service.common.IFindByIdAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelDtoAble;

public interface IUserService extends IFindByIdAble<User,Long>, ISaveModelAble<User>, IChangeScreenModeAble<Long, EScreenTheme> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);

}
