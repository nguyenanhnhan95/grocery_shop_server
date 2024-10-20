package com.example.grocery_store_sales_online.repository.role.impl;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.example.grocery_store_sales_online.enums.ERole;
import com.example.grocery_store_sales_online.model.person.QRole;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.projection.person.RoleProjection;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.role.IRoleRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Projections;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class RoleRepository extends BaseRepository<Role,Long> implements IRoleRepository {
    protected   final QRole role=QRole.role;

    public RoleRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(Role.class, em,criteriaBuilderFactory);
    }
    public List<Role> findByAliasRoles(String alias){
        BlazeJPAQuery<Role> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return jpaQuery.select(role).from(role).where(role.alias.eq(alias)).fetch();
    }

    @Override
    public Optional<Role> findByNameAndAlias(String name, String alias) {
        BlazeJPAQuery<Role> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return  Optional.ofNullable(jpaQuery.select(role).from(role).where(role.name.eq(name).and(role.alias.eq(alias))).fetchOne());
    }

    @Override
    public Optional<RoleProjection> findByIdRoleProjection(Long id) {
        try {
            BlazeJPAQuery<RoleProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
            List<RoleProjection> roleProjection = jpaQuery
                    .select(Projections.fields(RoleProjection.class,
                            role.id, role.name, role.alias, role.description, role.permissions))
                    .from(role)
//                    .where(role.id.eq(id))
                    .fetch();
            System.out.println(roleProjection.size());
            return Optional.ofNullable(null);
        }catch (Exception ex){
            log.error("Exception occurred while persisting RoleRepository:findByIdRoleProjection to database , Exception message {}", ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Role> findByName(String name){
        BlazeJPAQuery<Role> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return  Optional.ofNullable(jpaQuery.select(role).from(role).where(role.name.eq(name)).fetchOne());
    }

    @Override
    public QueryListResult<Role> getListResult(QueryParameter queryParameter) {
        BlazeJPAQuery<Role> query = search(queryParameter.getCriterias());
        List<Role> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<Role>builder().result(result).total(total).build();
    }
    public BlazeJPAQuery<Role> search(Map<String, Object> params) {
        String keyword = MapUtils.getString(params, "name");
        BlazeJPAQuery<Role> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        jpaQuery.select(role)
                .from(role);
        if (params != null && !params.isEmpty()) {
            if (StringUtils.isNotBlank(keyword)) {
                keyword = "%" + keyword + "%";
                jpaQuery.where(role.name.like(keyword));
            }
            if (MapUtils.getString(params, "includeDeleted") != null) {
                Boolean deleted = (Boolean) MapUtils.getObject(params, "includeDeleted");
                jpaQuery.where(role.deleted.eq(deleted));
            }
        }
        return jpaQuery;
    }

    @Override
    public List<RoleProjection> findAllProjection() {
        BlazeJPAQuery<RoleProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return  jpaQuery.select(Projections.constructor(RoleProjection.class,role.id,role.name))
                .from(role).fetch();
    }

    @Override
    public List<RoleProjection> listRoleEmployee() {
        BlazeJPAQuery<RoleProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return  jpaQuery.select(Projections.constructor(RoleProjection.class,role.id,role.name))
                .from(role)
                .where(role.alias.ne(ERole.USER.getCode()))
                .fetch();
    }
}
