package com.example.grocery_store_sales_online.repository.productItem;

import com.example.grocery_store_sales_online.projection.product.ProductItemProjection;
import com.example.grocery_store_sales_online.projection.product.ProductManageProjection;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface IProductItemRepository  {
    QueryListResult<ProductManageProjection> getListResultManage(QueryParameter queryParameter);
    List<ProductItemProjection> findByIdProduct(Long id);
}
