package com.example.grocery_store_sales_online.repository.address.impl;

import com.example.grocery_store_sales_online.model.address.Districts;
import com.example.grocery_store_sales_online.model.address.QDistricts;
import com.example.grocery_store_sales_online.projection.address.DistrictsProjection;
import com.example.grocery_store_sales_online.repository.address.IDistrictRepository;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DistrictRepository extends BaseRepository<Districts,Long> implements IDistrictRepository {
    protected QDistricts districts = QDistricts.districts;

    public DistrictRepository(EntityManager em) {
        super(Districts.class, em);
    }

    @Override
    public List<DistrictsProjection> listDistrictsByProvinceProjection(String province_code) {
        JPAQuery<DistrictsProjection> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(Projections.constructor(DistrictsProjection.class,districts.code,districts.name)).from(districts)
                .where(districts.provinces.code.eq(province_code)).fetch();
    }

    @Override
    public Optional<DistrictsProjection> findByCodeProjection(String code) {
        JPAQuery<DistrictsProjection> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(Projections.constructor(DistrictsProjection.class, districts.code, districts.name)).from(districts)
                .where(districts.code.eq(code)).fetchFirst());
    }

    @Override
    public Optional<Districts> findByCode(String code) {
        JPAQuery<DistrictsProjection> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(districts).from(districts)
                .where(districts.code.eq(code)).fetchFirst());
    }
}