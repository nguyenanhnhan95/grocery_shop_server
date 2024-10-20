package com.example.grocery_store_sales_online.repository.user.impl;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.enums.ERole;
import com.example.grocery_store_sales_online.model.person.QRole;
import com.example.grocery_store_sales_online.model.person.QUser;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.user.IUserRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.core.types.Projections;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository extends BaseRepository<User,Long> implements IUserRepository {
    protected final QUser user = QUser.user;
    protected final QRole role = QRole.role;
    public UserRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(User.class, em,criteriaBuilderFactory);
    }
    public Optional<User> findByEmail(String email){
        BlazeJPAQuery<User> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(user).from(user).where(user.email.eq(email)).fetchOne());
    }

    @Override
    public Optional<User> findByNameLogin(String nameLogin) {
        BlazeJPAQuery<User> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(user).from(user).where(user.nameLogin.eq(nameLogin)).fetchOne());
    }

    @Override
    public Optional<User> findByPhone(String email) {
        BlazeJPAQuery<User> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(user).from(user).where(user.phone.eq(email)).fetchOne());
    }

    @Override
    public Optional<User> findByIdCard(String idCard) {
        BlazeJPAQuery<User> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(user).from(user).where(user.idCard.eq(idCard)).fetchOne());
    }

    @Override
    public List<User> findAllEmployee() {
        BlazeJPAQuery<User> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return jpaQuery.select(user).from(user).where(user.deleted.eq(false).and(user.roles.any().alias.ne(ERole.USER.getCode()))).fetch();
    }

    @Override
    public Optional<User> findByIdEmployee(Long id) {
        BlazeJPAQuery<User> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(user).from(user).where(user.roles.any().alias.ne(ERole.USER.getCode())
                .and(user.id.eq(id))).fetchOne());
    }
    @Override
    public QueryListResult<EmployeeProjection> getListResultEmployee(QueryParameter queryParameter) {
        BlazeJPAQuery<EmployeeProjection> query = search(queryParameter.getCriterias());
        List<EmployeeProjection> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<EmployeeProjection>builder().result(result).total(total).build();
    }

    @Override
    public Optional<EmployeeProjection> findByIdEmployeeProjection(Long id) {
        BlazeJPAQuery<EmployeeProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(Projections.constructor(
                        EmployeeProjection.class,
                        user.id,
                        user.avatar,
                        user.name,
                        user.nameLogin,
                        user.phone,
                        user.email,
                        user.accountStatus,
                        user.idCard,
                        user.birthOfDate,
                        user.address,
                        user.provinces.code.as("provinces"),
                        user.districts.code.as("districts"),
                        user.wards.code.as("wards"),
                        Projections.list(
                                role.id
                        )
                )).from(user)
                .innerJoin(user.roles, role)
                .where(user.id.eq(id).and(user.deleted.eq(false).and(user.roles.any().alias.ne(ERole.USER.getCode())))).fetchFirst());
    }

    private BlazeJPAQuery<EmployeeProjection> search(Map<String, Object> params) {
        BlazeJPAQuery<EmployeeProjection> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        jpaQuery.select(Projections.constructor(
                EmployeeProjection.class, user.id, user.name, user.nameLogin
                , user.email, user.avatar, user.createDate, user.accountStatus
        )).from(user);
        jpaQuery.where(user.roles.any().alias.ne(ERole.USER.getCode()));
        if (params != null && !params.isEmpty()) {
            String keyword = MapUtils.getString(params, "name");
            String status = MapUtils.getString(params, "status");
            Long roleId = MapUtils.getLongValue(params, "roleId");
            if (StringUtils.isNotBlank(keyword)) {
                keyword = "%" + keyword + "%";
                jpaQuery.where(user.name.like(keyword));
            }
            if (StringUtils.isNoneBlank(status)) {
                EAccountStatus accountStatus;
                try {
                    accountStatus = EAccountStatus.valueOf(status);
                    jpaQuery.where(user.accountStatus.eq(accountStatus));
                } catch (Exception ex) {
                    log.error("Exception occurred while persisting EmployeeRepository:search to read status account , Exception message {}", ex.getMessage());
                }
            }
            if (roleId != null && roleId > 0) {
                jpaQuery.where(user.roles.any().id.eq(roleId));
            }
            if (MapUtils.getString(params, "includeDeleted") != null) {
                Boolean deleted = (Boolean) MapUtils.getObject(params, "includeDeleted");
                jpaQuery.where(user.deleted.eq(deleted));
            }
        }
        return jpaQuery;
    }



}
