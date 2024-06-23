package com.example.grocery_store_sales_online.service.common;

import java.util.Optional;

public interface IFindByIdAble<T,ID> {
    public Optional<T> findById(ID id);
}
