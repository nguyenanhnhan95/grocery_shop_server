package com.example.grocery_store_sales_online.service.common;

import java.util.Optional;

public interface IFindByAliasAble <T>{
    Optional<T> findByAlias(String alias);
}
