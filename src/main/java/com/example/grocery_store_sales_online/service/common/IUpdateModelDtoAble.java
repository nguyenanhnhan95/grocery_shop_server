package com.example.grocery_store_sales_online.service.common;

public interface IUpdateModelDtoAble<T,ID,DTO>{
    T updateModelDto(ID id, DTO model);
}
