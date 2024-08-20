package com.example.grocery_store_sales_online.service.common;

import java.util.Optional;

public interface IFindByEmailAble<T> {
    Optional<T> findByEmail(String email);
}
