package com.example.grocery_store_sales_online.repository.variation;

import com.example.grocery_store_sales_online.model.product.Variation;

import com.example.grocery_store_sales_online.projection.product.VariationProjection;
import com.example.grocery_store_sales_online.repository.base.IBaseRepository;
import com.example.grocery_store_sales_online.repository.common.IFindByName;
import com.example.grocery_store_sales_online.repository.common.IGetListResult;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface IVariationRepository extends IBaseRepository<Variation,Long>, IGetListResult<VariationProjection>, IFindByName<Variation> {


}
