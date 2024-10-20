package com.example.grocery_store_sales_online.payload;

import com.example.grocery_store_sales_online.components.MainMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MenuParentChildren {
    private MainMenu parent;
    private MainMenu children;
}
