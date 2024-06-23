package com.example.grocery_store_sales_online.service.employee;

import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService<Employee>  {
    QueryListResult<Employee> findAllSearch(QueryParameter queryParameter);
    List<Employee> findAll();
    public void saveEmployee(Employee employee);
    public Employee findByUserName(String name);
    Optional<Employee> findById(Long id);
}
