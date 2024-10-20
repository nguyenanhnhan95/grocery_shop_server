package com.example.grocery_store_sales_online.repository.image.impl;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.model.File.QImage;

import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.image.IImageRepository;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ImageRepository extends BaseRepository<Image,Long> implements IImageRepository {
    protected final QImage image = QImage.image;
    public ImageRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(Image.class, em,criteriaBuilderFactory);
    }

    @Override
    public Optional<Image> findByName(String name) {
        BlazeJPAQuery<Image> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(image).from(image)
                .where(image.name.eq(name)).fetchFirst());
    }
}
