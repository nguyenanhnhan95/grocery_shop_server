package com.example.grocery_store_sales_online.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class QueryListResult <T>{
    private List<T> result;
    private long total;
}
