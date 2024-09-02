package com.example.grocery_store_sales_online.service;


import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.projection.person.RoleProjection;
import com.example.grocery_store_sales_online.service.common.*;

import java.util.List;
import java.util.Set;

public interface IRoleService  extends IFindByAliasAble<Role>, ISaveModelAble<Role>, IFindByIdAble<Role,Long>, IGetResultListAble<Role>,
        IDeleteModelAble<Long>,ISaveModelDtoAble<Role,RoleDto>,IUpdateModelDtoAble<Role,Long,RoleDto> {
    List<RoleProjection> listRoleAlias();
    List<RoleProjection> listRoleEmployee();
    Set<Role> listRoleByIDs(List<Long> ids);
}
