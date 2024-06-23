package com.example.grocery_store_sales_online.service.employee;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.repository.employee.EmployeeRepository;
import com.example.grocery_store_sales_online.service.base.BaseService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService extends BaseService implements IEmployeeService<Employee> {
    private final EmployeeRepository employeeRepository;


    @Override
    public QueryListResult<Employee> findAllSearch(QueryParameter queryParameter) {
        return null;
    }

    @Override
    public List<Employee> findAll() {
        try {
            log.info("EmployeeService:findAll execution started.");
            return employeeRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findAll  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public void saveEmployee(Employee employee) {
        try {
            log.info("EmployeeService:saveEmployee execution started.");
            setMetaData(employee);
            setPersonCreate(employee);
            employeeRepository.saveModel(employee);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:saveEmployee save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL);
        }

    }

    @Override
    public Employee findByUserName(String name) {
        try {
            log.info("EmployeeService:findByUserName execution started.");
            return employeeRepository.findByUserName(name);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findByUserName  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try {
            log.info("EmployeeService:findById execution started.");
            return employeeRepository.findById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findById save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }
}
