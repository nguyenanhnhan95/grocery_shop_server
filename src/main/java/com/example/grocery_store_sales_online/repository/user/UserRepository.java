package com.example.grocery_store_sales_online.repository.user;
import com.example.grocery_store_sales_online.model.person.QUser;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<User,Long> {
    protected final QUser user = QUser.user;
    public UserRepository( EntityManager em) {
        super(User.class, em);
    }
    public User finByEmail(String email){
        JPAQuery<User> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(user).from(user).where(user.email.eq(email)).fetchOne();
    }

}
