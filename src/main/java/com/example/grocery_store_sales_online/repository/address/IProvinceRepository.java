package com.example.grocery_store_sales_online.repository.address;

import com.example.grocery_store_sales_online.model.address.Provinces;
import com.example.grocery_store_sales_online.projection.address.ProvincesProjection;
import com.example.grocery_store_sales_online.repository.common.IFindByCode;
import com.example.grocery_store_sales_online.repository.common.IFindByCodeProjection;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IProvinceRepository extends IFindByCodeProjection<ProvincesProjection,String>,IFindByCode<Provinces,String> {
}
