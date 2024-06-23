package com.example.grocery_store_sales_online.config;

import com.example.grocery_store_sales_online.components.MainMenu;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.account.Role;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.role.IRoleService;
import com.example.grocery_store_sales_online.utils.ResourceJsonLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class MenuAdminProperties {
    Logger logger = LoggerFactory.getLogger(MenuAdminProperties.class);
    private static final String CONFIG_FILE = "menu.json";
    @Autowired
    private IRoleService roleService;
    private List<MainMenu> mainMenus = new ArrayList<MainMenu>();

    public MenuAdminProperties() {
        try {
            MainMenuObject mainMenuObject = new ResourceJsonLoader().readValue(CONFIG_FILE, MainMenuObject.class);
            if (mainMenuObject != null) {
                mainMenus = mainMenuObject.getMainMenus();
            }
            logger.info("Initial authorization");
        } catch (Exception e) {
            logger.error("Loading main menu with error: {}", e.getMessage());
        }
    }
    public List<MainMenu> getMainMenus(UserPrincipal userPrincipal){
        try {
            logger.info("MenuAdminProperties:getMainMenus execution started.");
            List<MainMenu> result = new ArrayList<>();
            List<Role> roles = new ArrayList<>();
            for (GrantedAuthority permission:userPrincipal.getAuthorities()) {
                Optional<Role> roleTemp = roleService.findByAlias(permission.getAuthority());
                roleTemp.ifPresent(roles::add);
            }
            Set<String> permissionUser = roles.stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .collect(Collectors.toSet());
            result = mainMenus.stream()
                    .filter(menu -> menu.getRequiredPermissions().stream()
                            .anyMatch(permissionUser::contains))
                    .collect(Collectors.toList());
            return result;
        }catch (Exception ex){
            logger.error("Exception occurred while persisting MenuAdminProperties:getMainMenus to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }
}
