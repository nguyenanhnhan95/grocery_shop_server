package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.dto.person.EmployeeDto;
import com.example.grocery_store_sales_online.dto.person.EmployeeEditDto;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.service.common.*;


public interface IEmployeeService extends IFindByIdAble<User,Long>, IFindAllAble<User>,ISaveModelAble<User>,
        IFindByNameListAble<User>, IGetResultListAble<EmployeeProjection>, IDeleteModelAble<Long>,IFindByEmailAble<User>,
        ISaveModelDtoAble<User,EmployeeDto>, IFindByIdProjectionAble<EmployeeProjection,Long>,IUpdateModelDtoAble<User,Long, EmployeeEditDto>{
}
