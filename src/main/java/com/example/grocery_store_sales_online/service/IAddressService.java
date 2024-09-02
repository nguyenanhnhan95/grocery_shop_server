package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.model.address.Districts;
import com.example.grocery_store_sales_online.model.address.Provinces;
import com.example.grocery_store_sales_online.model.address.Wards;
import com.example.grocery_store_sales_online.projection.address.DistrictsProjection;
import com.example.grocery_store_sales_online.projection.address.ProvincesProjection;
import com.example.grocery_store_sales_online.projection.address.WardsProjection;

import java.util.List;

public interface IAddressService {
    List<WardsProjection> listWardsByCodeDistrict(String codeDistrict);
    List<DistrictsProjection> listDistrictsByCodeProvince(String codeProvince);
    List<Provinces> findAllProvinces();
    ProvincesProjection findByCodeProvinceProjection(String code);
    Districts findByCodeDistricts(String code);
    Provinces findByCodeProvinces(String code);
    Wards findByCodeWards(String code);
}
