package com.example.grocery_store_sales_online.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class MainMenu {
    private String title;
    private boolean isActive;
    private String href;
    private List<MainMenu> subMenus = new ArrayList<>();
    private Set<String> resources = new HashSet<>();
    private String iconClass;
    private boolean header;
    private Set<String> requiredPermissions = new HashSet<>();
}
