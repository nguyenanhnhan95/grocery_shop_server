package com.example.grocery_store_sales_online.service.common;

import java.util.Optional;

public interface IFindByIdAble<T,ID> {
    T findById(ID id);
}
