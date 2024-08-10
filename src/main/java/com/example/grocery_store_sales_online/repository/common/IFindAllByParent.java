package com.example.grocery_store_sales_online.repository.common;

import java.util.List;

public interface IFindAllByParent<T> {
    List<T> findAllByParent(T t);
}
