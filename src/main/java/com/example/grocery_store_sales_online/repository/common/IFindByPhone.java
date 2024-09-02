package com.example.grocery_store_sales_online.repository.common;

import java.util.Optional;

public interface IFindByPhone<T> {
    Optional<T> findByPhone(String email);
}
