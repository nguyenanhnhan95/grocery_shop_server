package com.example.grocery_store_sales_online.repository.Promotion;

import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.repository.base.IBaseRepository;
import com.example.grocery_store_sales_online.repository.common.IFindByCode;
import com.example.grocery_store_sales_online.repository.common.IFindByName;
import com.example.grocery_store_sales_online.repository.common.IGetListCode;
import com.example.grocery_store_sales_online.repository.common.IGetListResult;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IPromotionRepository extends IBaseRepository<Promotion,Long> , IGetListResult<Promotion>,
        IFindByCode<Promotion,String>,IFindByName<Promotion> , IGetListCode<Promotion> {
}
