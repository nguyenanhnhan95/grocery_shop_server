package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.components.Permission;
import com.example.grocery_store_sales_online.components.Scope;
import com.example.grocery_store_sales_online.config.AuthorizationProperties;
import com.example.grocery_store_sales_online.dto.person.RoleDto;
import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.enums.ERole;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.custom.mapper.person.RoleMapper;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.projection.person.RoleProjection;
import com.example.grocery_store_sales_online.repository.role.impl.RoleRepository;
import com.example.grocery_store_sales_online.service.IRoleService;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CommonUtils;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoleServiceImpl extends BaseServiceImpl implements IRoleService {
    private final RoleRepository roleRepository;
    private final AuthorizationProperties authorizationProperties;
    private final RoleMapper roleMapper;

    @Override
    public Optional<Role> findByAlias(String alias) {
        try {
            log.info("RoleService:findByAlias execution started.");
            return roleRepository.findByAlias(alias);
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
        } catch (Exception ex) {
            log.error("Exception occurred while persisting RoleService:findById  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public QueryListResult<Role> getListResult(String queryParameter) {
        try {
            log.info("RoleService:getListResult execution started.");
            return roleRepository.getListResult(readJsonQuery(queryParameter, QueryParameter.class));
        } catch (Exception ex) {
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
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting RoleService:deleteModel to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.DELETE_FAIL.getLabel(), EResponseStatus.DELETE_FAIL.getCode());
        }
    }

    @Override
    public Role saveModelDto(RoleDto model) {
        log.info("RoleService:saveModelDto execution started.");
        try {
            this.checkPermissions(model.getPermissions());
            Role role = roleMapper.convertDtoToRole(model);
            setPersonAction(role);
            setMetaData(role);
            return roleRepository.save(role);
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting RoleService:saveModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public Role updateModelDto(Long id, RoleDto model) {
        log.info("RoleService:updateModelDto execution started.");
        try {
            Optional<Role > role = findById(id);
            role.orElseThrow(()-> createValidationException("roleDto", "notification", CommonConstants.THIS_DATA_EDIT_FAIL));
            if(roleRepository.findByName(model.getName()).isEmpty()){
                throw  createValidationException("roleDto", "name", CommonConstants.THIS_FIELD_ALREADY_EXIST);
            }
            this.checkPermissions(model.getPermissions());
            roleMapper.updateDtoTo(model,role.get());
            setMetaData(role.get());
            setPersonAction(role.get());
            return roleRepository.saveModel(role.get());
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex){
            log.error("Exception occurred while persisting PromotionService:updateModelDto to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.EDIT_FAIL.getLabel(), EResponseStatus.EDIT_FAIL.getCode());
        }
//        return null;
    }
    private void checkPermissions(Set<String> setPermission){
        log.info("RoleService:checkPermissions execution started.");
        Set<Permission> permissions = authorizationProperties.getPermissions();
        if (setPermission != null) {
            boolean checkContain = permissions.stream()
                    .flatMap(permission -> permission.getScopes().stream())
                    .map(Scope::getId)
                    .anyMatch(setPermission::contains);

            if (!checkContain) {
                throw createValidationException("roleDto", "permissions", CommonConstants.THIS_FILE_ENTER_FAIL);
            }
        }
    }

    @Override
    public List<RoleProjection> findAllAble() {
        try {
            log.info("RoleService:listRoleAlias execution started.");
            return roleRepository.findAllProjection();
        }catch (Exception ex){
            log.error("Exception occurred while persisting RoleService:listRoleAlias to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<RoleProjection> listRoleEmployee() {
        try {
            log.info("RoleService:listRoleEmployee execution started.");
            return roleRepository.listRoleEmployee();
        }catch (Exception ex){
            log.error("Exception occurred while persisting RoleService:listRoleEmployee to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Set<Role> listRoleByIDs(List<Long> ids) {
        try {
            Set<Role> roles = new HashSet<>();
            ids.forEach(each -> {
                Optional<Role> role = roleRepository.findById(each);
                if (role.isPresent()) {
                    roles.add(role.get());
                } else {
                    throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_DATA.getLabel(), EResponseStatus.NOT_FOUND_DATA.getCode());
                }
            });
            return roles;
        }catch (ServiceBusinessExceptional ex){
            throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting RoleService:listRoleByIDs to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<Map<String, String>> findListAlias() {
        try {
            return Arrays.stream(ERole.values())
                    .map(status -> {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", status.getLabel());
                        map.put("name", status.getText());
                        return map;
                    })
                    .collect(Collectors.toList());

        }catch (Exception ex){
            log.error("Exception occurred while persisting RoleService:findListAlias to Enum EAccountStatus  , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
}