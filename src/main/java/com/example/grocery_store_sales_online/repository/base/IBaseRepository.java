package com.example.grocery_store_sales_online.repository.base;

import com.example.grocery_store_sales_online.model.product.Variation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IBaseRepository<T,ID> extends JpaRepository<T,ID> {
    Optional<T> findByIdMandatory(ID id);
    void clear();
    void detach(T entity);
}
