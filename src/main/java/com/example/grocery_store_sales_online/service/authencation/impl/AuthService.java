package com.example.grocery_store_sales_online.service.authencation.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.account.Role;
import com.example.grocery_store_sales_online.payload.UserResponse;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.authencation.IAuthService;
import com.example.grocery_store_sales_online.service.role.impl.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {
    private final RoleService roleService;
    @Override
    public Optional<UserResponse> getCurrentUser(UserPrincipal userPrincipal) {
        try {
            log.info("AuthService:getCurrentUser execution started.");
            UserResponse userResponse = null;
            if(userPrincipal !=null){
                List<String> roles = new ArrayList<>();
                List<String> permissions = new ArrayList<>();
                for (GrantedAuthority role: userPrincipal.getAuthorities()) {
                    Optional<Role> roleCurrent= roleService.findByAlias(role.getAuthority());
                    roles.add(roleCurrent.get().getAlias());
                    permissions.addAll(roleCurrent.get().getPermissions());
                }
                userResponse = UserResponse.builder().id(userPrincipal.getId())
                        .name(userPrincipal.getName())
                        .avatar(userPrincipal.getAvatar())
                        .authProvider(userPrincipal.getProvider())
                        .roles(roles).permission(permissions).build();

            }
            return Optional.ofNullable(userResponse);
        }catch (Exception ex){
            log.error("Exception occurred while persisting AuthService:getCurrentUser to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }
}
