package com.example.grocery_store_sales_online.repository.common;

import java.util.Optional;

public interface IFindByCode <T,ID>{
    Optional<T> findByCode(ID code);
}
