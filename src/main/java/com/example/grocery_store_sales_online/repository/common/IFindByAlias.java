package com.example.grocery_store_sales_online.repository.common;

import java.util.Optional;

public interface IFindByAlias <T>{
    Optional<T> findByAlias(String name);
}
