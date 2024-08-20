package com.example.grocery_store_sales_online.repository.common;

import java.util.Optional;

public interface IFindByEmail<T> {
    Optional<T> findByEmail(String email);
}
