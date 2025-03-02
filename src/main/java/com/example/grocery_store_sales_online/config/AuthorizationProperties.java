package com.example.grocery_store_sales_online.config;
import com.example.grocery_store_sales_online.components.Permission;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.utils.ResourceJsonLoader;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


@Component
public class AuthorizationProperties {
    Logger logger = LoggerFactory.getLogger(AuthorizationProperties.class);

    private static final String CONFIG_FILE = "authorization.json";

    @Getter
    private Set<Role> roles = new HashSet<Role>();
    @Getter
    private Set<Permission> permissions = new LinkedHashSet<>();

    public AuthorizationProperties() {
        try {
            AuthorizationObject authorizationObject = new ResourceJsonLoader().readValue(CONFIG_FILE, AuthorizationObject.class);
            if (authorizationObject != null) {
                roles = authorizationObject.getRoles();
                permissions = authorizationObject.getPermissions();
            }
            logger.info("Initial authorization");
        } catch (Exception e) {
            logger.error("Loading authorization properties with error: {}", e.getMessage());
        }
    }
}
