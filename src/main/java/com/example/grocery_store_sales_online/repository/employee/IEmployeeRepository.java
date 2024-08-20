package com.example.grocery_store_sales_online.repository.employee;

import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.repository.common.IFindByEmail;
import com.example.grocery_store_sales_online.repository.common.IFindByName;
import com.example.grocery_store_sales_online.repository.common.IFindNameLogin;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IEmployeeRepository extends IFindByName<Employee>, IFindNameLogin<Employee>, IFindByEmail<Employee> {

}
