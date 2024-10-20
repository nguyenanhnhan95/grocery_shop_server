package com.example.grocery_store_sales_online.projection.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariationProjection {
    private Long id;
    private String name;
    private String description;

    public VariationProjection() {
    }
    @QueryProjection
    public VariationProjection(Long id) {
        this.id = id;
    }
    @QueryProjection
    public VariationProjection(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
