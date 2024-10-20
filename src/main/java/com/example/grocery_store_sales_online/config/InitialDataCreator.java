package com.example.grocery_store_sales_online.config;


import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.enums.ERole;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.repository.role.impl.RoleRepository;
import com.example.grocery_store_sales_online.service.IUserService;
import com.example.grocery_store_sales_online.service.IProductCategoryService;

import com.example.grocery_store_sales_online.service.ISocialProviderService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Component
@RequiredArgsConstructor
public class InitialDataCreator implements ApplicationListener<ApplicationReadyEvent> {
    private final AuthorizationProperties authorizationProperties;
    private final RoleRepository roleRepository;
    private final IUserService userService;
    private final IProductCategoryService productCategoryService;
    private final CategoryProductProperties categoryProductProperties;
    private final ISocialProviderService socialProviderService;
    Logger logger = LoggerFactory.getLogger(InitialDataCreator.class);

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {

        initRole();
        initManager();
        initCategoryProduct();
    }

    public void initRole() {
        if(roleRepository.findAll().isEmpty()){
            roleRepository.saveAll(authorizationProperties.getRoles());
        }

    }

    public void initCategoryProduct() {
        for (ProductCategory productCategory : categoryProductProperties.getProductCategories()) {
            createUpdateProductCategory(productCategory);
        }
    }

    public void initManager() {
        boolean noUserCreated = userService.findAllAble().isEmpty();
        if (noUserCreated) {
            BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
            User admin = new User();
            admin.setNameLogin("Admin");
            admin.setName("Nhàn");
            admin.setEmail("nguyenanhnhan95@gmail.com");
            admin.setPassword(bCryptPasswordEncoder.encode("123123"));
            admin.setAccountStatus(EAccountStatus.ACTIVATED);
            List<Role> roleAdmin = roleRepository.findByAliasRoles(ERole.ADMIN.getCode());
            if (!roleAdmin.isEmpty() && roleAdmin.get(0).getName().equals("Admin")) {
                Set<Role> roles = new HashSet<Role>();
                roles.add(roleAdmin.get(0));
                admin.setRoles(roles);
            }
            User saveUser =userService.saveModel(admin);
            SocialProvider socialProvider = new SocialProvider();
            socialProvider.setProviderId(UUID.randomUUID().toString());
            socialProvider.setProvider(AuthProvider.local);
            socialProvider.setUser(saveUser);
            socialProviderService.saveModel(socialProvider);
        }
    }



    private void createUpdateProductCategory(ProductCategory productCategory) {
        ProductCategory current = productCategoryService.findByHref(productCategory.getHref());
        if (current != null) {
            current.setHref(productCategory.getHref());
            current.setDescription(productCategory.getDescription());
            current.setName(productCategory.getName());
            if (current.getParentCategory() == null && !productCategory.getChildren().isEmpty()) {
                for (ProductCategory each : productCategory.getChildren()) {
                    each.setParentCategory(current);
                    createUpdateProductCategory(each);
                }
                ;
            }
            productCategoryService.saveModel(current);
        } else {
            if (productCategory.getChildren() != null && productCategory.getChildren().isEmpty()) {
                productCategoryService.saveModel(productCategory);
            } else {
                ProductCategory parent = productCategoryService.saveModel(productCategory);
                if (parent != null) {
                    for (ProductCategory each : productCategory.getChildren()) {
                        each.setParentCategory(parent);
                        productCategoryService.saveModel(each);
                    }
                }
            }
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
