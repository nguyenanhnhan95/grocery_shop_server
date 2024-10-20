package com.example.grocery_store_sales_online.service.common;

public interface IFindByIdProjectionAble<T,ID>{
    T findByIdProjection(ID id);
}
