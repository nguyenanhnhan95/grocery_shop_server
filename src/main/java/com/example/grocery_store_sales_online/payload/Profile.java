package com.example.grocery_store_sales_online.payload;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EScreenTheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Profile {
    private Long id;
    private String name;
    private String avatar;
    private AuthProvider authProvider;
    private List<String> roles;
    private List<String> permission;
    private EScreenTheme screenTheme;
}
