package com.example.grocery_store_sales_online.service.common;

import com.example.grocery_store_sales_online.utils.QueryListResult;

import java.util.List;

public interface IGetResultListAble<T>{
    QueryListResult<T> getListResult(String queryParameter);
}
