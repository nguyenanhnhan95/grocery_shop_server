package com.example.grocery_store_sales_online.model.product;

import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "variation")
public class Variation extends Model {
    private String name;
    private String description;

    public Variation() {
    }
    @QueryProjection
    public Variation(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

}
