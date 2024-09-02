package com.example.grocery_store_sales_online.repository.address.impl;

import com.example.grocery_store_sales_online.model.address.QWards;
import com.example.grocery_store_sales_online.model.address.Wards;
import com.example.grocery_store_sales_online.projection.address.WardsProjection;
import com.example.grocery_store_sales_online.repository.address.IWardRepository;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WardRepository extends BaseRepository<Wards,Long> implements IWardRepository {
    protected QWards ward=QWards.wards;
    public WardRepository( EntityManager em) {
        super(Wards.class, em);
    }

    @Override
    public List<WardsProjection> listWardsByCodeDistrict(String code_district) {
        JPAQuery<WardsProjection> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(Projections.constructor(WardsProjection.class,ward.code,ward.name)).from(ward)
                .where(ward.districts.code.eq(code_district)).fetch();
    }

    @Override
    public Optional<WardsProjection> findByCodeProjection(String code) {
        JPAQuery<WardsProjection> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(Projections.constructor(WardsProjection.class, ward.code, ward.name)).from(ward)
                .where(ward.code.eq(code)).fetchFirst());
    }

    @Override
    public Optional<Wards> findByCode(String code) {
        JPAQuery<Wards> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(ward).from(ward)
                .where(ward.code.eq(code)).fetchFirst());
    }
}
