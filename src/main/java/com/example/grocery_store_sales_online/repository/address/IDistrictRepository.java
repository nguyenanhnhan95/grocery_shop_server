package com.example.grocery_store_sales_online.repository.address;

import com.example.grocery_store_sales_online.model.address.Districts;
import com.example.grocery_store_sales_online.projection.address.DistrictsProjection;
import com.example.grocery_store_sales_online.repository.common.IFindByCode;
import com.example.grocery_store_sales_online.repository.common.IFindByCodeProjection;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IDistrictRepository extends IFindByCodeProjection<DistrictsProjection,String>,IFindByCode<Districts,String> {
    List<DistrictsProjection> listDistrictsByProvinceProjection(String province_code);
}
