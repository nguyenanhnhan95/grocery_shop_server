package com.example.grocery_store_sales_online.repository.common;

import java.util.Optional;

public interface IFindByName<T>{
    Optional<T> findByName(String name);
}
