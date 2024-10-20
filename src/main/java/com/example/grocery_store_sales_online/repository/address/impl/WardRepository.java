package com.example.grocery_store_sales_online.repository.address.impl;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.example.grocery_store_sales_online.model.address.QWards;
import com.example.grocery_store_sales_online.model.address.Wards;
import com.example.grocery_store_sales_online.projection.address.WardsProjection;
import com.example.grocery_store_sales_online.repository.address.IWardRepository;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.querydsl.core.types.Projections;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WardRepository extends BaseRepository<Wards,Long> implements IWardRepository {
    protected QWards ward=QWards.wards;
    public WardRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(Wards.class, em,criteriaBuilderFactory);
    }

    @Override
    public List<WardsProjection> listWardsByCodeDistrict(String code_district) {
        BlazeJPAQuery<WardsProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return jpaQuery.select(Projections.constructor(WardsProjection.class,ward.code,ward.name)).from(ward)
                .where(ward.districts.code.eq(code_district)).fetch();
    }

    @Override
    public Optional<WardsProjection> findByCodeProjection(String code) {
        BlazeJPAQuery<WardsProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(Projections.constructor(WardsProjection.class, ward.code, ward.name)).from(ward)
                .where(ward.code.eq(code)).fetchFirst());
    }

    @Override
    public Optional<Wards> findByCode(String code) {
        BlazeJPAQuery<Wards> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(ward).from(ward)
                .where(ward.code.eq(code)).fetchFirst());
    }
}
