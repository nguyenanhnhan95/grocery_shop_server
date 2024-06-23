package com.example.grocery_store_sales_online.config;

import com.example.grocery_store_sales_online.components.MainMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MainMenuObject {
    private List<MainMenu> mainMenus = new ArrayList<>();
}
