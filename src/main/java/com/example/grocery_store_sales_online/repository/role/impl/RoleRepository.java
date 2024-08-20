package com.example.grocery_store_sales_online.repository.role.impl;

import com.example.grocery_store_sales_online.model.person.QRole;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.role.IRoleRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.jpa.impl.JPAQuery;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RoleRepository extends BaseRepository<Role,Long> implements IRoleRepository {
    protected   final QRole role=QRole.role;

    public RoleRepository(EntityManager em) {
        super(Role.class, em);
    }
    public Optional<Role> findByAlias(String alias){
        JPAQuery<Role> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(role).from(role).where(role.alias.eq(alias)).fetchOne());
    }
    @Override
    public Optional<Role> findByName(String name){
        JPAQuery<Role> jpaQuery = new JPAQuery<>(em);
        return  Optional.ofNullable(jpaQuery.select(role).from(role).where(role.name.eq(name)).fetchOne());
    }

    @Override
    public QueryListResult<Role> getListResult(QueryParameter queryParameter) {
        JPAQuery<Role> query = search(queryParameter.getCriterias());
        List<Role> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<Role>builder().result(result).total(total).build();
    }
    public JPAQuery<Role> search(Map<String, Object> params) {
        String keyword = MapUtils.getString(params, "name");
        JPAQuery<Role> jpaQuery = new JPAQuery<>(em);
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

}
