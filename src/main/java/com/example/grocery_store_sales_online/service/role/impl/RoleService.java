package com.example.grocery_store_sales_online.service.role.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.account.Role;
import com.example.grocery_store_sales_online.repository.role.RoleRepository;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.role.IRoleService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoleService extends BaseService implements IRoleService {
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

    @Override
    public QueryListResult<Role> getListResult(String queryParameter) {
        try {
            log.info("RoleService:getListResult execution started.");
            return roleRepository.getListResult(readJsonQuery(queryParameter, QueryParameter.class));
        }catch (Exception ex){
            log.error("Exception occurred while persisting RoleService.getListResult to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public void deleteModel(Long id) {
        try {
            log.info("PromotionService:deleteModel execution started.");
            Optional<Role> role = findById(id);
            if (role.isEmpty()) {
                throw new ServiceBusinessExceptional(EResponseStatus.NO_EXISTING.getLabel(), EResponseStatus.NO_EXISTING.getCode());
            }
            setMetaData(role.get());
            setPersonAction(role.get());
            role.get().setDeleted(true);
            roleRepository.save(role.get());
        }catch (ServiceBusinessExceptional ex){
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionService:deleteModel to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.DELETE_FAIL.getLabel(), EResponseStatus.DELETE_FAIL.getCode());
        }
    }
}
