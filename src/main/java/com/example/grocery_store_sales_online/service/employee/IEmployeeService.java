package com.example.grocery_store_sales_online.service.employee;

import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.service.common.*;


public interface IEmployeeService extends IFindByIdAble<Employee,Long>,  IFindAll<Employee>,ISaveModelAble<Employee>,IChangeScreenModeAble<Long, EScreenTheme>,
        IFindByNameListAble<Employee>, IGetResultListAble<Employee>, IDeleteModelAble<Long>,IFindByNameLoginAble<Employee>,IFindByEmailAble<Employee>{
}
