package com.example.grocery_store_sales_online.payload;

import com.example.grocery_store_sales_online.enums.EScreenTheme;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class UserScreeThemeOnly {
    private Long id;
    private @NotNull EScreenTheme screenTheme;
}
