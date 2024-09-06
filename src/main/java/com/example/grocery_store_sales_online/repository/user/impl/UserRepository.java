package com.example.grocery_store_sales_online.repository.user.impl;
import com.example.grocery_store_sales_online.model.person.QUser;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.user.IUserRepository;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User,Long> implements IUserRepository {
    protected final QUser user = QUser.user;
    public UserRepository( EntityManager em) {
        super(User.class, em);
    }
    public Optional<User> findByEmail(String email){
        JPAQuery<User> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(user).from(user).where(user.email.eq(email)).fetchOne());
    }

    @Override
    public Optional<User> findByNameLogin(String nameLogin) {
        JPAQuery<User> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(user).from(user).where(user.nameLogin.eq(nameLogin)).fetchOne());
    }

    @Override
    public Optional<User> findByPhone(String email) {
        JPAQuery<User> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(user).from(user).where(user.phone.eq(email)).fetchOne());
    }

    @Override
    public Optional<User> findByIdCard(String idCard) {
        JPAQuery<User> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(user).from(user).where(user.idCard.eq(idCard)).fetchOne());
    }
}
