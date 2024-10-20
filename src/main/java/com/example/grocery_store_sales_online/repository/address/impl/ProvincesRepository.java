package com.example.grocery_store_sales_online.repository.address.impl;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.example.grocery_store_sales_online.model.address.Provinces;
import com.example.grocery_store_sales_online.model.address.QProvinces;
import com.example.grocery_store_sales_online.projection.address.ProvincesProjection;
import com.example.grocery_store_sales_online.repository.address.IProvinceRepository;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.querydsl.core.types.Projections;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProvincesRepository extends BaseRepository<Provinces,Long> implements IProvinceRepository {
    protected  QProvinces provinces = QProvinces.provinces;
    public ProvincesRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(Provinces.class, em,criteriaBuilderFactory);
    }

    @Override
    public Optional<ProvincesProjection> findByCodeProjection(String code) {
        BlazeJPAQuery<ProvincesProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(Projections.constructor(ProvincesProjection.class,provinces.code,provinces.name)).from(provinces)
                .where(provinces.code.eq(code)).fetchFirst());
    }

    @Override
    public Optional<Provinces> findByCode(String code) {
        BlazeJPAQuery<Provinces> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(provinces).from(provinces)
                .where(provinces.code.eq(code)).fetchFirst());
    }
}
