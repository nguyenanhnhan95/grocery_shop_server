package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.service.common.IGetResultListAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelDtoAble;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService extends IGetResultListAble<ProductProjection>,ISaveModelDtoAble<Product,ProductDto>, ISaveModelAble<Product> {
    void preProcessSave(ProductDto productDto, List<MultipartFile> imagesProduct, List<MultipartFile> imageProductItems) ;

}
