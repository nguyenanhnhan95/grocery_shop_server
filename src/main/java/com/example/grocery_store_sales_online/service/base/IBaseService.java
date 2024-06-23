package com.example.grocery_store_sales_online.service.base;

import com.example.grocery_store_sales_online.utils.QueryListResult;

public interface IBaseService<T> {
    QueryListResult<T> finAll(String queryParameter);
}
