package com.example.grocery_store_sales_online.repository.user;


import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.repository.common.IFindByEmail;
import com.example.grocery_store_sales_online.repository.common.IFindByIdCard;
import com.example.grocery_store_sales_online.repository.common.IFindByPhone;
import com.example.grocery_store_sales_online.repository.common.IFindNameLogin;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends IFindByEmail<User>, IFindNameLogin<User>
        , IFindByPhone<User>, IFindByIdCard<User> {
    List<User> findAllEmployee();
    Optional<User> findByIdEmployee(Long id);
    QueryListResult<EmployeeProjection> getListResultEmployee(QueryParameter queryParameter);
    Optional<EmployeeProjection> findByIdEmployeeProjection(Long id);
}
