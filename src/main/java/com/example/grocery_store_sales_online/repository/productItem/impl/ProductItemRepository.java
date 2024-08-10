package com.example.grocery_store_sales_online.repository.productItem.impl;

import com.example.grocery_store_sales_online.model.product.ProductItem;
import com.example.grocery_store_sales_online.model.product.QProductItem;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.productItem.IProductItemRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ProductItemRepository extends BaseRepository<ProductItem,Long> implements IProductItemRepository {
    protected QProductItem productItem = QProductItem.productItem;
    public ProductItemRepository( EntityManager em) {
        super(ProductItem.class, em);
    }
}
