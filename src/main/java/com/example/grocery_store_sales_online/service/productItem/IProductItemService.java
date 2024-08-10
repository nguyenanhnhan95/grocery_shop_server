package com.example.grocery_store_sales_online.service.productItem;

import com.example.grocery_store_sales_online.dto.product.ProductItemDto;
import com.example.grocery_store_sales_online.model.product.ProductItem;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelDtoAble;

public interface IProductItemService extends ISaveModelDtoAble<ProductItem, ProductItemDto>, ISaveModelAble<ProductItem> {
}
