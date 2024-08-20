package com.example.grocery_store_sales_online.service.employee.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.repository.employee.impl.EmployeeRepository;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.employee.IEmployeeService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService extends BaseService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        try {
            log.info("EmployeeService:findAll execution started.");
            return employeeRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findAll  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Employee saveModel(Employee employee) {
        try {
            log.info("EmployeeService:saveEmployee execution started.");
            setMetaData(employee);
            setPersonAction(employee);
            return employeeRepository.saveModel(employee);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:saveEmployee save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }
    @Override
    public Optional<Employee> findByName(String name) {
        try {
            log.info("EmployeeService:findByUserName execution started.");
            return employeeRepository.findByName(name);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findByUserName  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try {
            log.info("EmployeeService:findById execution started.");
            return employeeRepository.findById(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting EmployeeService:findById save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public void deleteModel(Long aLong) {

    }


    @Override
    public QueryListResult<Employee> getListResult(String queryParameter) {
        return null;
    }


    @Override
    public void ChangeScreenMode(Long id, EScreenTheme screenTheme) {
        try {
            log.info("EmployeeService:ChangeScreenMode execution started.");
            Optional<Employee> employee = findById(id);
            employee.ifPresent(value -> {
                value.setScreenTheme(screenTheme);
                this.saveModel(value);
            });
        }catch (Exception ex){
            log.error("Exception occurred while persisting EmployeeService:ChangeScreenMode save to change screen mode , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.CHANGE_SCREEN_LIGHT_FAIL.getLabel(), EResponseStatus.CHANGE_SCREEN_LIGHT_FAIL.getCode());
        }
    }

    @Override
    public Optional<Employee> findByNameLogin(String nameLogin) {
        try {
            log.info("EmployeeService:findByNameLogin execution started.");
            return employeeRepository.findByNameLogin(nameLogin);
        }catch (Exception ex){
            log.error("Exception occurred while persisting EmployeeService:findByNameLogin to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        try {
            log.info("EmployeeService:findByNameLogin execution started.");
            return employeeRepository.findByEmail(email);
        }catch (Exception ex){
            log.error("Exception occurred while persisting EmployeeService:findByNameLogin to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
}
