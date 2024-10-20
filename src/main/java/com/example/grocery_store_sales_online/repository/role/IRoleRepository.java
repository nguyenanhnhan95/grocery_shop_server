package com.example.grocery_store_sales_online.repository.role;

import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.projection.person.RoleProjection;
import com.example.grocery_store_sales_online.repository.common.IFindAllProjection;
import com.example.grocery_store_sales_online.repository.common.IFindByAlias;
import com.example.grocery_store_sales_online.repository.common.IFindByName;
import com.example.grocery_store_sales_online.repository.common.IGetListResult;

import java.util.List;
import java.util.Optional;

public interface IRoleRepository extends IFindByName<Role>, IGetListResult<Role>, IFindAllProjection<RoleProjection> {

    List<RoleProjection> listRoleEmployee();
    List<Role> findByAliasRoles(String alias);
    Optional<Role> findByNameAndAlias(String name,String alias);
    Optional<RoleProjection> findByIdRoleProjection(Long id);
}
