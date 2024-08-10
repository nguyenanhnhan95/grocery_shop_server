package com.example.grocery_store_sales_online.repository.product;

import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.projection.ProductProjection;
import com.example.grocery_store_sales_online.repository.common.IGetListResult;

public interface IProductRepository extends IGetListResult<ProductProjection> {
}
