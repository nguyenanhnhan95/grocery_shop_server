package com.example.grocery_store_sales_online.projection.person;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class RoleProjection {
    private Long id;
    private String name;
    private String alias;
    private String description;
    private Set<String> permissions = new HashSet<>();

    public RoleProjection() {
    }
    @QueryProjection
    public RoleProjection(Long id,String name) {
        this.id = id;
        this.name=name;
    }
    @QueryProjection
    public RoleProjection(Long id) {
        this.id = id;
    }


}
