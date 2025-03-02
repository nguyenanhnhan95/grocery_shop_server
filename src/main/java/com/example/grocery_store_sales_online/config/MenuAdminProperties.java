package com.example.grocery_store_sales_online.config;

import com.example.grocery_store_sales_online.components.MainMenu;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.repository.socialProvider.impl.SocialProviderRepository;
import com.example.grocery_store_sales_online.repository.user.impl.UserRepository;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.utils.ResourceJsonLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.grocery_store_sales_online.utils.CommonConstants.PATH_ADMIN;
import static com.example.grocery_store_sales_online.utils.CommonConstants.PATH_DASH_BOARD;


@Component
public class MenuAdminProperties {
    Logger logger = LoggerFactory.getLogger(MenuAdminProperties.class);
    private static final String CONFIG_FILE = "menu.json";
    @Autowired
    private UserRepository userRepository;
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
            User user = userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(),
                            EResponseStatus.NOT_FOUND_USER.getCode()));

            Set<String> permissionUser = user.getRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .collect(Collectors.toSet());
            for (MainMenu mainMenu: mainMenus) {
                if(mainMenu.isHeader()|| mainMenu.getRequiredPermissions().stream().anyMatch(permissionUser::contains)){
                    result.add(mainMenu);
                    continue;
                }
                for (MainMenu subMenu: mainMenu.getSubMenus()) {
                    if(subMenu.getRequiredPermissions().stream().anyMatch(permissionUser::contains)){
                        result.add(mainMenu);
                        break;
                    }
                }
            }
            return result;
        }catch (ServiceBusinessExceptional ex){
            logger.error("Exception occurred while persisting MenuAdminProperties:getMainMenus to database , Exception message {}", ex.getMessage());
            throw ex;
        }catch (Exception ex){
            logger.error("Exception occurred while persisting MenuAdminProperties:getMainMenus to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
    public MainMenu getMainMenuPath(UserPrincipal userPrincipal,String path){
        try{
            logger.info("MenuAdminProperties:getMainMenuPath(path) execution started.");
            List<MainMenu> mainMenus = getMainMenus(userPrincipal);
            if(mainMenus.isEmpty()){
                throw new ServiceBusinessExceptional(EResponseStatus.NOT_PERMISSION_PAGE.getLabel(),EResponseStatus.NOT_PERMISSION_PAGE.getCode());
            }
            if(path.equals(PATH_DASH_BOARD) || path.equals(PATH_ADMIN)){
                return mainMenus.get(0);
            }
            for (MainMenu parent: mainMenus) {
                if(parent.getHref()!=null && parent.getHref().equals(handleSubPath(path))){
                    return parent;
                }
                if(!parent.getSubMenus().isEmpty()){
                    for (MainMenu children: parent.getSubMenus()) {
                        for (String resource:children.getResources()) {

                            if (path.length() >= resource.length() && resource.equals(handleSubPath(path))){
                                return children;
                            }
                        }
                    }
                }
            }
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_PERMISSION_PAGE.getLabel(),EResponseStatus.NOT_PERMISSION_PAGE.getCode());
        }catch (ServiceBusinessExceptional ex){
            logger.error("Exception occurred while MenuAdminProperties:getMainMenuPath(path) to get Menu , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex){
            logger.error("Exception occurred while MenuAdminProperties:getMainMenuPath(path) to get Menu , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
    public MainMenu getMenuParentByPathChildren(UserPrincipal userPrincipal,String pathChildren){
        try{
            logger.info("MenuAdminProperties:getMenuParentByPathChildren(path) execution started.");
            List<MainMenu> mainMenus = getMainMenus(userPrincipal);
//            this.getMainMenuPath(userPrincipal,pathChildren);
            for (MainMenu parent: mainMenus) {
                if(!parent.getSubMenus().isEmpty()){
                    for (MainMenu children: parent.getSubMenus()) {
                        if(children.getHref().equals(pathChildren)){
                            return parent;
                        }
                    }
                }
            }
            return  null;
        } catch (Exception ex){
            logger.error("Exception occurred while MenuAdminProperties:getMainMenuPath(path) to get Menu , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
    private String handleSubPath(String href){
        try {
            if (href.contains("?")|| href.contains("edit") || href.contains("add")) {
                return href.substring(0, href.lastIndexOf('/'));
            }
            return href;
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return null;
        }
    }
}
