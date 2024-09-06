package com.example.grocery_store_sales_online.repository.employee.impl;

import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.model.person.QEmployee;
import com.example.grocery_store_sales_online.model.person.QRole;
import com.example.grocery_store_sales_online.projection.file.ImageProjection;
import com.example.grocery_store_sales_online.projection.person.EmployeeProjection;
import com.example.grocery_store_sales_online.projection.person.RoleProjection;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.employee.IEmployeeRepository;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import jakarta.persistence.EntityManager;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class EmployeeRepository extends BaseRepository<Employee, Long> implements IEmployeeRepository {
    protected final QEmployee employee = QEmployee.employee;
    protected final QRole role = QRole.role;

    public EmployeeRepository(EntityManager em) {
        super(Employee.class, em);
    }

    //    public QueryListResult<Employee> findAll(QueryParameter queryParameter) {
//        JPAQuery<Employee> query = search(queryParameter.getCriterias());
//        List<Employee> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
//        long total = query.fetchCount();
//        return QueryListResult.<Employee>builder().result(result).total(total).build();
//    }
    @Override
    public Optional<Employee> findByName(String name) {
        JPAQuery<Employee> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(employee).from(employee)
                .where(employee.name.eq(name)).fetchFirst());
    }

    @Override
    public Optional<Employee> findByNameLogin(String nameLogin) {
        JPAQuery<Employee> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(employee).from(employee)
                .where(employee.nameLogin.eq(nameLogin)).fetchFirst());
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        JPAQuery<Employee> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(employee).from(employee)
                .where(employee.email.eq(email)).fetchFirst());
    }
    @Override
    public Optional<Employee> findByPhone(String phone) {
        JPAQuery<Employee> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(employee).from(employee)
                .where(employee.phone.eq(phone)).fetchFirst());
    }
    @Override
    public Optional<Employee> findByIdCard(String idCard) {
        JPAQuery<Employee> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(employee).from(employee)
                .where(employee.idCard.eq(idCard)).fetchFirst());
    }
    @Override
    public Optional<EmployeeProjection> findByIdProjection(Long id) {
        JPAQuery<EmployeeProjection> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(Projections.constructor(
                        EmployeeProjection.class,
                        employee.id,
                        employee.avatar,
                        employee.name,
                        employee.nameLogin,
                        employee.phone,
                        employee.email,
                        employee.accountStatus,
                        employee.idCard,
                        employee.birthOfDate,
                        employee.address,
                        employee.provinces.code.as("provinces"),
                        employee.districts.code.as("districts"),
                        employee.wards.code.as("wards"),
                        Projections.list(
                                role.id
                        )
                )).from(employee)
                .innerJoin(employee.roles, role)
                .where(employee.id.eq(id)).fetchFirst());
    }

    public JPAQuery<EmployeeProjection> search(Map<String, Object> params) {
        JPAQuery<EmployeeProjection> jpaQuery = new JPAQuery<>(em);
        jpaQuery.select(Projections.constructor(
                EmployeeProjection.class, employee.id, employee.name, employee.nameLogin
                , employee.email, employee.avatar, employee.createDate, employee.accountStatus
        )).from(employee);
        if (params != null && !params.isEmpty()) {
            String keyword = MapUtils.getString(params, "name");
            String status = MapUtils.getString(params, "status");
            Long roleId = MapUtils.getLongValue(params, "roleId");
            if (StringUtils.isNotBlank(keyword)) {
                keyword = "%" + keyword + "%";
                jpaQuery.where(employee.name.like(keyword));
            }
            if (StringUtils.isNoneBlank(status)) {
                EAccountStatus accountStatus;
                try {
                    accountStatus = EAccountStatus.valueOf(status);
                    jpaQuery.where(employee.accountStatus.eq(accountStatus));
                } catch (Exception ex) {
                    log.error("Exception occurred while persisting EmployeeRepository:search to read status account , Exception message {}", ex.getMessage());
                }
            }
            if (roleId != null && roleId > 0) {
                jpaQuery.where(employee.roles.any().id.eq(roleId));
            }
            if (MapUtils.getString(params, "includeDeleted") != null) {
                Boolean deleted = (Boolean) MapUtils.getObject(params, "includeDeleted");
                jpaQuery.where(employee.deleted.eq(deleted));
            }
        }
        return jpaQuery;
    }


    @Override
    public QueryListResult<EmployeeProjection> getListResult(QueryParameter queryParameter) {
        JPAQuery<EmployeeProjection> query = search(queryParameter.getCriterias());
        List<EmployeeProjection> result = page(query, queryParameter.getSize(), queryParameter.getPage()).fetch();
        long total = query.fetchCount();
        return QueryListResult.<EmployeeProjection>builder().result(result).total(total).build();
    }



}
