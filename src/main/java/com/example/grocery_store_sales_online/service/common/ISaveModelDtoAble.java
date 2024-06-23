package com.example.grocery_store_sales_online.service.common;

public interface ISaveModelDtoAble<T,DTO>{
    T saveModelDto(DTO model);
}
