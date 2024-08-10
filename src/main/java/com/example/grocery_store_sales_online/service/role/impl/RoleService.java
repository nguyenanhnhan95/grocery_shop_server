package com.example.grocery_store_sales_online.service.role.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.account.Role;
import com.example.grocery_store_sales_online.repository.role.RoleRepository;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.role.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoleService extends BaseService implements IRoleService<Role> {
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByAlias(String alias) {
        try {
            log.info("RoleService:findByAlias execution started.");
            return Optional.ofNullable(roleRepository.findByAlias(alias));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting RoleService:findByAlias to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Role saveModel(Role model) {
        try {
            log.info("RoleService:saveModel execution started.");
            setMetaData(model);
            return roleRepository.save(model);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting RoleService:saveModel save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<Role> findById(Long id) {
        try {
            log.info("RoleService:findById execution started.");
            return roleRepository.findById(id);
        }catch (Exception ex){
            log.error("Exception occurred while persisting RoleService:findById  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

}
