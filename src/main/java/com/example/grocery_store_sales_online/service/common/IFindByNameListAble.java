package com.example.grocery_store_sales_online.service.common;

import java.util.List;
import java.util.Optional;

public interface IFindByNameListAble<T> {
    Optional<T> findByName(String name);
}
