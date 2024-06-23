package com.example.grocery_store_sales_online.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshToken {
    private String token;
}
