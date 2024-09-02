package com.example.grocery_store_sales_online.projection.address;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WardsProjection {
    private String code;
    private String name;

    public WardsProjection() {
    }
    @QueryProjection
    public WardsProjection(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
