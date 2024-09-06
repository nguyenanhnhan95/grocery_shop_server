package com.example.grocery_store_sales_online.repository.common;

import java.util.Optional;

public interface IFindByIdCard<T> {
    Optional<T> findByIdCard(String idCard);
}
