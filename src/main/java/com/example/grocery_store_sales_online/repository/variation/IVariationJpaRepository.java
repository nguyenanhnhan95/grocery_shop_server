package com.example.grocery_store_sales_online.repository.variation;

import com.example.grocery_store_sales_online.model.product.Variation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVariationJpaRepository extends JpaRepository<Variation,Long> {
}
