package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.enums.ERole;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.payload.Profile;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.IProfileService;
import com.example.grocery_store_sales_online.service.IEmployeeService;
import com.example.grocery_store_sales_online.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements IProfileService {
    private final RoleServiceImpl roleService;
    private final IUserService userService;
    private final IEmployeeService employeeService;
    @Override
    public Profile getCurrentProfile(UserPrincipal userPrincipal) {
        try {
            log.info("ProfileService:getCurrentProfile execution started.");
            Profile userResponse = null;
            if(userPrincipal !=null){
                List<String> roles = new ArrayList<>();
                List<String> permissions = new ArrayList<>();
                for (GrantedAuthority role: userPrincipal.getAuthorities()) {
                    Optional<Role> roleCurrent= roleService.findByAlias(role.getAuthority());
                    roles.add(roleCurrent.get().getAlias());
                    permissions.addAll(roleCurrent.get().getPermissions());
                }
                userResponse = Profile.builder().id(userPrincipal.getId())
                        .name(userPrincipal.getName())
                        .avatar(userPrincipal.getAvatar())
                        .idProvider(userPrincipal.getIdProvider())
                        .roles(roles).permission(permissions)
                        .screenTheme(userPrincipal.getScreenTheme()).build();

            }
            if(userPrincipal==null){
                throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(), EResponseStatus.NOT_FOUND_USER.getCode());
            }
            return userResponse;
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProfileService:getCurrentProfile to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public void changeScreenMode(Profile profile) {
        try {
            log.info("ProfileService:changeScreenMode execution started.");
            boolean isUserRole = profile.getRoles().stream()
                    .anyMatch(role -> role.equals(ERole.USER.getLabel()));

            if (isUserRole) {
                userService.ChangeScreenMode(profile.getId(), profile.getScreenTheme());
            } else {
                employeeService.ChangeScreenMode(profile.getId(), profile.getScreenTheme());
            }
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProfileService:changeScreenMode to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.EDIT_FAIL.getLabel(), EResponseStatus.EDIT_FAIL.getCode());
        }

    }

    @Override
    public List<Map<String, String>> listAccountStatus() {
        try {
            return Arrays.stream(EAccountStatus.values())
                    .map(status -> {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", status.getStyle());
                        map.put("name", status.getLabel());
                        return map;
                    })
                    .collect(Collectors.toList());

        }catch (Exception ex){
            log.error("Exception occurred while persisting ProfileService:listAccountStatus to Enum EAccountStatus  , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

}
