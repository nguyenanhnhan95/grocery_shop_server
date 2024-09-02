package com.example.grocery_store_sales_online.repository.common;

import java.util.Optional;

public interface IFindByCodeProjection<T,ID> {
    Optional<T> findByCodeProjection(ID id);
}
