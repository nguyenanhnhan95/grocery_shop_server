package com.example.grocery_store_sales_online.repository.employee;

import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.repository.common.*;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IEmployeeRepository extends IFindByName<Employee>, IFindNameLogin<Employee>, IFindByEmail<Employee>,
        IGetListResult<EmployeeProjection>, IFindByIdProjection<EmployeeProjection,Long>,IFindByPhone<Employee> {

}
