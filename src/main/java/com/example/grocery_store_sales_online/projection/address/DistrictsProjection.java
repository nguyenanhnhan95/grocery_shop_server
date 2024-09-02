package com.example.grocery_store_sales_online.projection.address;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DistrictsProjection {

    private String code;
    private String name;

    public DistrictsProjection() {
    }
    @QueryProjection
    public DistrictsProjection(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
