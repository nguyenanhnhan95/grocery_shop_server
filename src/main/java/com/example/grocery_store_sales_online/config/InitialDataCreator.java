package com.example.grocery_store_sales_online.config;


import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.model.account.Role;
import com.example.grocery_store_sales_online.service.employee.impl.EmployeeService;
import com.example.grocery_store_sales_online.service.productCategory.IProductCategoryService;

import com.example.grocery_store_sales_online.service.role.impl.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class InitialDataCreator implements ApplicationListener<ApplicationReadyEvent> {
    private final AuthorizationProperties authorizationProperties;
    private final RoleService roleService;
    private final EmployeeService employeeService;
    private final IProductCategoryService productCategoryService;
    private final PasswordEncoder passwordEncoder;
    private final CategoryProductProperties categoryProductProperties;

    Logger logger = LoggerFactory.getLogger(InitialDataCreator.class);

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initRole();
        initManager();
        initCategoryProduct();
    }

    public void initRole() {
        for (Role role : authorizationProperties.getRoles()) {
            createUpdateRole(role);
        }
    }

    public void initCategoryProduct() {
        for (ProductCategory productCategory : categoryProductProperties.getProductCategories()) {
            createUpdateProductCategory(productCategory);
        }
    }

    public void initManager() {
        boolean noUserCreated = employeeService.findAll().isEmpty();
        if (noUserCreated) {
            Employee admin = new Employee();
            Employee employee = new Employee();
            admin.setName("Admin");
            employee.setName("employee");
            admin.setPassword(passwordEncoder.encode("123123"));
            employee.setPassword(passwordEncoder.encode("123123"));
            admin.setAccountStatus(EAccountStatus.ACTIVATED);
            employee.setAccountStatus(EAccountStatus.ACTIVATED);
            admin.setProvider(AuthProvider.local);
            employee.setProvider(AuthProvider.local);
            Optional<Role> roleAdmin = roleService.findByAlias("ROLE_ADMIN");
            Optional<Role> roleEmployee = roleService.findByAlias("ROLE_EMPLOYEE");
            if (roleAdmin.isPresent() ) {
                Set<Role> roles = new HashSet<Role>();
                roles.add(roleAdmin.get());
                admin.setRoles(roles);
            }
            if (roleEmployee.isPresent() ) {
                Set<Role> roles = new HashSet<Role>();
                roles.add(roleEmployee.get());
                employee.setRoles(roles);
            }
            employeeService.saveEmployee(admin);
            employeeService.saveEmployee(employee);
        }
    }

    private void createUpdateRole(Role role) {
        Optional<Role> current = roleService.findByAlias(role.getAlias());
        if (current.isPresent()) {
            Role roleCurrent = current.get();
            roleCurrent.setDescription(role.getDescription());
            roleCurrent.setName(role.getName());
            if (roleCurrent.getPermissions().isEmpty()) {
                roleCurrent.setPermissions(role.getPermissions());
            }
            roleService.saveModel(roleCurrent);
        } else {
            roleService.saveModel(role);
        }
    }

    private void createUpdateProductCategory(ProductCategory productCategory) {
        ProductCategory current = productCategoryService.findByHref(productCategory.getHref());
        if (current != null) {
            current.setHref(productCategory.getHref());
            current.setDescription(productCategory.getDescription());
            current.setName(productCategory.getName());
            if (current.getParentCategory() == null && !productCategory.getChildren().isEmpty()) {
                for (ProductCategory each : productCategory.getChildren()) {
                    each.setParentCategory(current);
                    createUpdateProductCategory(each);
                }
                ;
            }
            productCategoryService.saveProductCategory(current);
        } else {
            if (productCategory.getChildren() != null && productCategory.getChildren().isEmpty()) {
                productCategoryService.saveProductCategory(productCategory);
            } else {
                ProductCategory parent = productCategoryService.saveProductCategory(productCategory);
                if (parent != null) {
                    for (ProductCategory each : productCategory.getChildren()) {
                        each.setParentCategory(parent);
                        productCategoryService.saveProductCategory(each);
                    }
                }
            }
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
