package com.example.grocery_store_sales_online.repository.productCategory.impl;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.model.product.QProductCategory;
import com.example.grocery_store_sales_online.projection.product.ProductCategoryProjection;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;

import com.example.grocery_store_sales_online.repository.productCategory.IProductCategoryRepository;
import com.querydsl.core.types.Projections;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductCategoryRepository extends BaseRepository<ProductCategory,Long> implements IProductCategoryRepository {
    protected QProductCategory productCategory= QProductCategory.productCategory;
    public ProductCategoryRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(ProductCategory.class, em,criteriaBuilderFactory);
    }
    public Optional<ProductCategory> findByHref(String href){
        BlazeJPAQuery<ProductCategoryProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(productCategory).from(productCategory)
                .where(productCategory.href.eq(href)).fetchOne());
    }
    public List<ProductCategoryProjection> findAllParent(){
        BlazeJPAQuery<ProductCategoryProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return jpaQuery.select(Projections.constructor(ProductCategoryProjection.class,
                        productCategory.id,productCategory.name,productCategory.href,productCategory.description)).from(productCategory)
                .where(productCategory.parentCategory.isNull()).fetch();
    }
    @Override
    public List<ProductCategoryProjection> findAllByParent(Long id) {
        BlazeJPAQuery<ProductCategoryProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return jpaQuery.select(Projections.constructor(ProductCategoryProjection.class,
                        productCategory.id,productCategory.name,productCategory.href,productCategory.description)).from(productCategory)
                .where(productCategory.parentCategory.id.eq(id)).fetch();
    }


    public List<ProductCategoryProjection> findAllChildren(ProductCategory category) {
        BlazeJPAQuery<ProductCategory> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return jpaQuery.select(Projections.constructor(ProductCategoryProjection.class,
                        productCategory.id,productCategory.name,productCategory.href,productCategory.description)).from(productCategory)
                .where(productCategory.parentCategory.id.eq(category.getId())).fetch();
    }

    @Override
    public List<ProductCategoryProjection> findAllChildren() {
        BlazeJPAQuery<ProductCategory> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return jpaQuery.select(Projections.constructor(ProductCategoryProjection.class,
                        productCategory.id,productCategory.name,productCategory.href,productCategory.description)).from(productCategory)
                .where(productCategory.parentCategory.id.isNotNull()).fetch();
    }



}
