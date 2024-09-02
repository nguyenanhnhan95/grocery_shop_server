package com.example.grocery_store_sales_online.repository.product;

import com.example.grocery_store_sales_online.model.File.QImage;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.model.product.QProduct;
import com.example.grocery_store_sales_online.model.product.QProductCategory;
import com.example.grocery_store_sales_online.projection.file.ImageProjection;
import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository extends BaseRepository<Product, Long> implements IProductRepository {
    protected QProduct product = QProduct.product;
    private QProductCategory productCategory = QProductCategory.productCategory;
    protected QImage image = QImage.image;

    public ProductRepository(EntityManager em) {
        super(Product.class, em);
    }

    public JPAQuery<ProductProjection> search(Map<String, Object> params) {
        String keyword = MapUtils.getString(params, "name");
        JPAQuery<ProductProjection> jpaQuery = new JPAQuery<>(em);
        jpaQuery.select(Projections.constructor(
                        ProductProjection.class,
                        product.id,
                        product.name,
                        product.brand,
                        product.description,
                        product.productCategory.name.as("categoryName"),
                        Projections.list(
                                Projections.fields(
                                        ImageProjection.class,
                                        image.id,
                                        image.imageUrl,
                                        image.small
                                )

                        )))
                .from(product)
                .innerJoin(product.images, image);
        if (StringUtils.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
            jpaQuery.where(product.name.like(keyword));
        }

        return jpaQuery;
    }

    @Override
    public QueryListResult<ProductProjection> getListResult(QueryParameter queryParameter) {
        JPAQuery<ProductProjection> query = search(queryParameter.getCriterias());
        List<ProductProjection> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<ProductProjection>builder().result(result).total(total).build();
    }
}
