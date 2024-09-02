package com.example.grocery_store_sales_online.repository.user;


import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.repository.common.IFindByEmail;
import com.example.grocery_store_sales_online.repository.common.IFindByPhone;
import com.example.grocery_store_sales_online.repository.common.IFindNameLogin;

public interface IUserRepository extends IFindByEmail<User>, IFindNameLogin<User>, IFindByPhone<User> {
}
