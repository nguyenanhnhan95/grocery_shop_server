package com.example.grocery_store_sales_online.repository.product;

import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.repository.common.IGetListResult;

public interface IProductRepository extends IGetListResult<ProductProjection> {
}
