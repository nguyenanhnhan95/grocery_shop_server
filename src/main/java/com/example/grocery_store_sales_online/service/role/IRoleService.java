package com.example.grocery_store_sales_online.service.role;

import com.example.grocery_store_sales_online.service.common.IFindByAliasAble;
import com.example.grocery_store_sales_online.service.common.IFindByIdAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelDtoAble;

public interface IRoleService <Role>extends IFindByAliasAble<Role>, ISaveModelAble<Role>, IFindByIdAble<Role,Long> {

}
