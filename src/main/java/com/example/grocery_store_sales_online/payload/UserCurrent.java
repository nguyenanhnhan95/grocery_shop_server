package com.example.grocery_store_sales_online.payload;

import com.example.grocery_store_sales_online.enums.EScreenTheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserCurrent {
    private Long id;
    private String name;
    private String avatar;
    private String idProvider;
    private Set<String> roles;
    private Set<String> permission;
    private EScreenTheme screenTheme;
}
