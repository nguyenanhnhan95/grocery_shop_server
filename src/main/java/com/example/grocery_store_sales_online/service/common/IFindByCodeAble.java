package com.example.grocery_store_sales_online.service.common;

import java.util.Optional;

public interface IFindByCodeAble <T>{
    Optional<T> findByCode(String code);
}
