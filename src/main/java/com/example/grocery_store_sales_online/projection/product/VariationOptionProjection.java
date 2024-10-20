package com.example.grocery_store_sales_online.projection.product;

import com.example.grocery_store_sales_online.model.product.Variation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class VariationOptionProjection {
    private Long id;
    private String name;
    private String description;

    private VariationProjection variation;
    public VariationOptionProjection() {}

    @QueryProjection
    public VariationOptionProjection(Long id, String name, String description, VariationProjection variation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.variation = variation;
    }

}
