package com.example.grocery_store_sales_online.service.common;

import java.util.Optional;

public interface IFindByNameLoginAble <T>{
    Optional<T> findByNameLogin(String nameLogin);
}
