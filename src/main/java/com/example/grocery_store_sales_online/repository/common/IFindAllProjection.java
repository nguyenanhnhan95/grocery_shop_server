package com.example.grocery_store_sales_online.repository.common;

import java.util.List;

public interface IFindAllProjection <T>{
    List<T> findAllProjection();
}
