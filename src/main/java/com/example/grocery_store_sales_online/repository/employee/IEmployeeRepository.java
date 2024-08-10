package com.example.grocery_store_sales_online.repository.employee;

import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.repository.common.IFindByName;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IEmployeeRepository extends IFindByName<Employee> {
}
