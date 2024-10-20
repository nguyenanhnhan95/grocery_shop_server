package com.example.grocery_store_sales_online.repository.product.impl;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blazebit.persistence.querydsl.JPQLNextExpressions;
import com.example.grocery_store_sales_online.model.File.QImage;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.model.product.QProduct;
import com.example.grocery_store_sales_online.model.product.QProductItem;

import com.example.grocery_store_sales_online.model.product.QVariationOption;
import com.example.grocery_store_sales_online.model.shop.QPromotion;
import com.example.grocery_store_sales_online.projection.product.ProductItemProjection;
import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.product.IProductRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.core.types.Projections;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class ProductRepository extends BaseRepository<Product, Long> implements IProductRepository {
    protected QProduct product = QProduct.product;
    protected QProductItem productItem = QProductItem.productItem;

    protected QImage image = QImage.image;

    protected QPromotion promotion=QPromotion.promotion;

    protected QVariationOption variationOption=QVariationOption.variationOption;

    public ProductRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(Product.class, em, criteriaBuilderFactory);
    }

    private BlazeJPAQuery<ProductProjection> search(Map<String, Object> params) {
        BlazeJPAQuery<ProductProjection> jpaQuery = new BlazeJPAQuery<>(em, criteriaBuilderFactory);
        jpaQuery.select(Projections.constructor(
                        ProductProjection.class,
                        product.id,
                        product.name,
                        product.brand,
                        product.productCategory.name.as("productCategory"),
                        productItem.qtyInStock.sum().as("qtyInStock"),
                        JPQLNextExpressions.groupConcat(image.small, ",").as("images")
                ))
                .from(product)
                .innerJoin(product.images, image)
                .leftJoin(productItem).on(productItem.product.eq(product))
                .groupBy(product.id, product.name, product.brand, product.description, product.productCategory.name);
        if (params != null && !params.isEmpty()) {
            String keyword = MapUtils.getString(params, "name");
            if (StringUtils.isNotBlank(keyword)) {
                keyword = "%" + keyword + "%";
                jpaQuery.where(product.name.like(keyword));
            }
            Long keyProductCategory = MapUtils.getLong(params, "productCategory");
            if (keyProductCategory != null) {
                jpaQuery.where(product.productCategory.id.eq(keyProductCategory));
            }
        }

        return jpaQuery;
    }

    @Override
    public QueryListResult<ProductProjection> getListResult(QueryParameter queryParameter) {
        BlazeJPAQuery<ProductProjection> query = search(queryParameter.getCriterias());
        List<ProductProjection> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = result.size();
        return QueryListResult.<ProductProjection>builder().result(result).total(total).build();
    }

    @Override
    public Optional<ProductProjection> findByIdFormEdit(Long id) {
        BlazeJPAQuery<ProductProjection> jpaQuery = new BlazeJPAQuery<>(em, criteriaBuilderFactory);
        return Optional.ofNullable(
                jpaQuery.select(Projections.constructor(
                                ProductProjection.class,
                                product.id,
                                product.name,
                                product.brand,
                                product.description,
                                product.variation.id.as("variation"),
                                product.productCategory.id.as("productCategory"),
                                JPQLNextExpressions.groupConcat(image.imageUrl, ",").as("images")
                        ))
                        .from(product)  // Tạo root entity chính xác// Explicit join for product items
                        .leftJoin(product.images, image)  // Explicit join for product images// Explicit join for variation options
                        .where(product.id.eq(id))
                        .groupBy(
                                product.id,
                                product.name,
                                product.brand,
                                product.description,
                                product.productCategory.id,
                                product.variation.id
                        ).fetchOne()
        );
    }
}
