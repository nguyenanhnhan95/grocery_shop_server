package com.example.grocery_store_sales_online.repository.productCategory;

import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.model.product.QProductCategory;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductCategoryRepository extends BaseRepository<ProductCategory,Long> {
    protected QProductCategory productCategory= QProductCategory.productCategory;
    public ProductCategoryRepository(EntityManager em) {
        super(ProductCategory.class, em);
    }
    public Optional<ProductCategory> findByHref(String href){
        JPAQuery<ProductCategory> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(productCategory).from(productCategory)
                .where(productCategory.href.eq(href)).fetchOne());
    }
    public List<ProductCategory> findAllParent(){
        JPAQuery<ProductCategory> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(productCategory).from(productCategory)
                .where(productCategory.parentCategory.isNull()).fetch();
    }
    public List<ProductCategory> listProductCategoryChildren(ProductCategory category) {
        JPAQuery<ProductCategory> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(productCategory).from(productCategory)
                .where(productCategory.parentCategory.id.eq(category.getId())).fetch();
    }

}
