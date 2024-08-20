package com.example.grocery_store_sales_online.repository.common;

import java.util.Optional;

public interface IFindNameLogin <T>{
    Optional<T> findByNameLogin(String nameLogin);
}
