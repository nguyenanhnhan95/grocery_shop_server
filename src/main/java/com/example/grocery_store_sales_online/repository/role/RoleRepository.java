package com.example.grocery_store_sales_online.repository.role;
import com.example.grocery_store_sales_online.model.account.QRole;
import com.example.grocery_store_sales_online.model.account.Role;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository extends BaseRepository<Role,Long> {
    protected   final QRole role=QRole.role;

    public RoleRepository(EntityManager em) {
        super(Role.class, em);
    }
    public Role findByAlias(String alias){
        JPAQuery<Role> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(role).from(role).where(role.alias.eq(alias)).fetchOne();
    }
}
