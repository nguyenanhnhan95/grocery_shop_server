package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.dto.person.EmployeeDto;
import com.example.grocery_store_sales_online.dto.person.EmployeeEditDto;
import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.service.common.*;


public interface IEmployeeService extends IFindByIdAble<Employee,Long>,  IFindAll<Employee>,ISaveModelAble<Employee>,IChangeScreenModeAble<Long, EScreenTheme>,
        IFindByNameListAble<Employee>, IGetResultListAble<EmployeeProjection>, IDeleteModelAble<Long>,IFindByNameLoginAble<Employee>,IFindByEmailAble<Employee>,
        ISaveModelDtoAble<Employee,EmployeeDto>,IFindByIdProjection<EmployeeProjection,Long>,IUpdateModelDtoAble<Employee,Long, EmployeeEditDto>{
}
