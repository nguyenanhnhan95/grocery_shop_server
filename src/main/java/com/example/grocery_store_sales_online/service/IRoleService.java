package com.example.grocery_store_sales_online.service;


import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.dto.person.RoleEditDto;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.projection.person.RoleProjection;
import com.example.grocery_store_sales_online.service.common.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IRoleService  extends  ISaveModelAble<Role>, IFindByIdAble<Role,Long>, IGetResultListAble<Role>, IFindByIdProjectionAble<RoleProjection,Long>,
        IDeleteModelAble<Long>,ISaveModelDtoAble<Role, RoleDto>,IUpdateModelDtoAble<Role,Long, RoleEditDto>,IFindAllAble<RoleProjection> {
    List<RoleProjection> listRoleEmployee();
    Set<Role> listRoleByIDs(List<Long> ids);
    List<Map<String,String>> findListAlias();
    List<Role> findByAliasRoles(String alias);
    Optional<Role> findByNameAndAlias(String name, String alias);
    List<Map<String,String>> listNameRoleByERole(String nameERole);
}
