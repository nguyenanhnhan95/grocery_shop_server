package com.example.grocery_store_sales_online.service.user;

import com.example.grocery_store_sales_online.service.common.IFindByIdAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelDtoAble;

public interface IUserService <User> extends IFindByIdAble<User,Long>, ISaveModelAble<User> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);

}
