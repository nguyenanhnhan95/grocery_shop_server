package com.example.grocery_store_sales_online.repository.role;

import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.projection.person.RoleProjection;
import com.example.grocery_store_sales_online.repository.common.IFindAllProjection;
import com.example.grocery_store_sales_online.repository.common.IFindByAlias;
import com.example.grocery_store_sales_online.repository.common.IFindByName;
import com.example.grocery_store_sales_online.repository.common.IGetListResult;

import java.util.List;

public interface IRoleRepository extends IFindByName<Role>, IGetListResult<Role>, IFindByAlias<Role>, IFindAllProjection<RoleProjection> {

    List<RoleProjection> listRoleEmployee();
}
