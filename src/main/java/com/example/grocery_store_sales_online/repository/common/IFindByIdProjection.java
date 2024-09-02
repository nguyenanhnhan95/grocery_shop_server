package com.example.grocery_store_sales_online.repository.common;

import java.util.Optional;

public interface IFindByIdProjection <T,ID>{
     Optional<T> findByIdProjection(ID id);
}
