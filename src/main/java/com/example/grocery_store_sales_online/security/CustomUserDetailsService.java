package com.example.grocery_store_sales_online.security;

import com.example.grocery_store_sales_online.enums.ErrorCode;
import com.example.grocery_store_sales_online.exception.ResourceNotFoundException;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.model.person.User;

import com.example.grocery_store_sales_online.service.employee.impl.EmployeeService;
import com.example.grocery_store_sales_online.service.user.impl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        log.info("CustomUserDetailsService:loadUserByUsername execution started.");
        User user = userService.findByEmail(email);
        if (user != null) {
            return UserPrincipal.createUser(user, "ROLE_USER");
        } else {
            Employee employee = employeeService.findByUserName(email);
            if(employee!=null){
                return UserPrincipal.createEmployee(employee, employee.getRoles());
            }
            throw new UsernameNotFoundException("User not found with : " + email);
        }
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.findById(id).orElse(null);
        if (user != null) {
            return UserPrincipal.createUser(user, "ROLE_USER");
        } else {
            throw new ResourceNotFoundException("User", "id", id, ErrorCode.USER_NOT_FOUND);
        }
    }
    @Transactional
    public UserDetails loadEmployeeById(Long id){
        Employee employee =employeeService.findById(id).orElse(null);
        if(employee!=null){
            return UserPrincipal.createEmployee(employee,employee.getRoles());
        }else {
            throw new ResourceNotFoundException("Nhân viên", "id", id, ErrorCode.USER_NOT_FOUND);
        }
    }

}
