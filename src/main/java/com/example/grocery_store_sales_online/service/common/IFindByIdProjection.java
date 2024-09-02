package com.example.grocery_store_sales_online.service.common;

public interface IFindByIdProjection<T,ID>{
    T findByIdProjection(ID id);
}
