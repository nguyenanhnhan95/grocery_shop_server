package com.example.grocery_store_sales_online.repository.image.impl;

import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.model.QImage;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.model.person.QEmployee;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.image.IImageRepository;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ImageRepository extends BaseRepository<Image,Long> implements IImageRepository {
    protected final QImage image = QImage.image;
    public ImageRepository( EntityManager em) {
        super(Image.class, em);
    }

    @Override
    public Optional<Image> findByName(String name) {
        JPAQuery<Employee> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(image).from(image)
                .where(image.name.eq(name)).fetchFirst());
    }
}
