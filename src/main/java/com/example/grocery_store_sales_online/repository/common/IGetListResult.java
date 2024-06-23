package com.example.grocery_store_sales_online.repository.common;

import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;

public interface IGetListResult<T> {
    QueryListResult<T> getListResult(QueryParameter queryParameter);
}
