package com.example.grocery_store_sales_online.service.common;

public interface IFindByIdAndIdRelationshipAble<T,ID> {
    T findByIdAndRelationshipAble(ID id);
}
