package com.example.grocery_store_sales_online.repository.address;

import com.example.grocery_store_sales_online.model.address.Wards;
import com.example.grocery_store_sales_online.projection.address.WardsProjection;
import com.example.grocery_store_sales_online.repository.common.IFindByCode;
import com.example.grocery_store_sales_online.repository.common.IFindByCodeProjection;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IWardRepository extends IFindByCodeProjection<WardsProjection,String>,IFindByCode<Wards,String> {
    List<WardsProjection> listWardsByCodeDistrict(String code_district);
}
