package com.example.grocery_store_sales_online.service.role;


import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.service.common.*;

public interface IRoleService  extends IFindByAliasAble<Role>, ISaveModelAble<Role>, IFindByIdAble<Role,Long>, IGetResultListAble<Role>,
        IDeleteModelAble<Long>,ISaveModelDtoAble<Role,RoleDto>,IUpdateModelDtoAble<Role,Long,RoleDto> {

}
