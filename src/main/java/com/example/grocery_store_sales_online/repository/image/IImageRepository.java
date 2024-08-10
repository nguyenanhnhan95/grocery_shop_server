package com.example.grocery_store_sales_online.repository.image;

import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.repository.common.IFindByName;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IImageRepository extends IFindByName<Image> {
}
