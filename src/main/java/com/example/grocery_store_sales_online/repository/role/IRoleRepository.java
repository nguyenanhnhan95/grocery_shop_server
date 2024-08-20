package com.example.grocery_store_sales_online.repository.role;

import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.repository.common.IFindByAlias;
import com.example.grocery_store_sales_online.repository.common.IFindByName;
import com.example.grocery_store_sales_online.repository.common.IGetListResult;

public interface IRoleRepository extends IFindByName<Role>, IGetListResult<Role>, IFindByAlias<Role> {
}
