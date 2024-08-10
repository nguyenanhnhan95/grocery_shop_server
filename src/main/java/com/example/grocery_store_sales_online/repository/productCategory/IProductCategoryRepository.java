package com.example.grocery_store_sales_online.repository.productCategory;

import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.repository.common.IFindAllByParent;
import com.example.grocery_store_sales_online.repository.common.IFindAllChildren;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IProductCategoryRepository extends IFindAllByParent<ProductCategory>, IFindAllChildren<ProductCategory> {
}
