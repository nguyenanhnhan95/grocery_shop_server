package com.example.grocery_store_sales_online.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class QueryParameter {
    public final static int UNLIMITED_SIZE = -1;

    private int size;
    private int page;


    @Builder.Default
    private Map<String, Object> criterias = new HashMap<>();
}
