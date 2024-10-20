package com.example.grocery_store_sales_online.repository.productItem.impl;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blazebit.persistence.querydsl.JPQLNextExpressions;
import com.example.grocery_store_sales_online.model.File.QImage;
import com.example.grocery_store_sales_online.model.product.*;
import com.example.grocery_store_sales_online.model.shop.QPromotion;
import com.example.grocery_store_sales_online.projection.file.ImageProjection;
import com.example.grocery_store_sales_online.projection.product.ProductItemProjection;
import com.example.grocery_store_sales_online.projection.product.ProductManageProjection;
import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.productItem.IProductItemRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProductItemRepository extends BaseRepository<ProductItem,Long> implements IProductItemRepository {
    protected QProductItem productItem = QProductItem.productItem;

    protected QProduct product=QProduct.product;

    protected QProductCategory productCategory= QProductCategory.productCategory;
    protected QVariationOption variationOption= QVariationOption.variationOption;

    protected QPromotion promotion = QPromotion.promotion;

    protected QImage image=QImage.image;
    public ProductItemRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(ProductItem.class, em,criteriaBuilderFactory);
    }

    @Override
    public QueryListResult<ProductManageProjection> getListResultManage(QueryParameter queryParameter) {
        BlazeJPAQuery<ProductManageProjection> query = searchManage(queryParameter.getCriterias());
        List<ProductManageProjection> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<ProductManageProjection>builder().result(result).total(total).build();
    }

    @Override
    public List<ProductItemProjection> findByIdProduct(Long id) {
        BlazeJPAQuery<ProductProjection> jpaQuery = new BlazeJPAQuery<>(em, criteriaBuilderFactory);
        return jpaQuery.select(Projections.constructor(
                        ProductItemProjection.class,
                        productItem.id,
                        JPQLNextExpressions.groupConcat(image.imageUrl, ",").as("images"),
                        productItem.price,
                        productItem.qtyInStock,
                        productItem.sku,
                        JPQLNextExpressions.groupConcat(promotion.id.stringValue(), ",").as("promotions"),
                        JPQLNextExpressions.groupConcat(variationOption.id.stringValue(), ",").as("variationOptions")
        )).from(productItem)
                .innerJoin(productItem.product,product)
                .leftJoin(productItem.promotions, promotion)  // Explicit join for promotions
                .leftJoin(productItem.variationOptions, variationOption)
                .leftJoin(productItem.images, image)
                .where(productItem.product.id.eq(id))
                .groupBy(
                        productItem.id,
                        productItem.price,
                        productItem.qtyInStock,
                        productItem.sku
                )
                .fetch();
    }

    private BlazeJPAQuery<ProductManageProjection> searchManage(Map<String, Object> params) {
        BlazeJPAQuery<ProductManageProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        jpaQuery.select(Projections.constructor(ProductManageProjection.class,productItem.id,product.name,
                        Projections.list(
                                Projections.fields(
                                        ImageProjection.class,
                                        image.id,
                                        image.imageUrl,
                                        image.small
                                )

                        ),product.brand,productCategory.name.as("productCategory"),Expressions.stringTemplate("GROUP_CONCAT({0})", variationOption.name).as("optionVariation")),
                        productItem.price,productItem.qtyInStock,productItem.sku, Expressions.stringTemplate("GROUP_CONCAT({0})", promotion.code).as("promotion")).from(productItem)
                .innerJoin(productItem.product,product)
                .innerJoin(product.productCategory,productCategory)
                .innerJoin(productItem.promotions,promotion)
                .innerJoin(productItem.variationOptions,variationOption)
                .innerJoin(product.images, image)
                .groupBy(productItem.id, product.name, product.brand, productCategory.name, productItem.price, productItem.qtyInStock, productItem.sku, image.id, image.imageUrl, image.small);
        return jpaQuery;
    }
}
