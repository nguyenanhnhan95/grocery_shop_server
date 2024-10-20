package com.example.grocery_store_sales_online.repository.token;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.example.grocery_store_sales_online.model.InvalidatedToken;
import com.example.grocery_store_sales_online.model.QInvalidatedToken;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InvalidatedTokenRepository extends BaseRepository<InvalidatedToken,Long> {
//    asda
    protected QInvalidatedToken invalidatedToken=QInvalidatedToken.invalidatedToken;
    public InvalidatedTokenRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(InvalidatedToken.class, em,criteriaBuilderFactory);
    }
    public Optional<InvalidatedToken> findByIdToken(String token){
        BlazeJPAQuery<InvalidatedToken> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return Optional.ofNullable(jpaQuery.select(invalidatedToken).from(invalidatedToken)
                .where(invalidatedToken.idToken.eq(token)).fetchFirst());
    }
    public List<InvalidatedToken> findAllToken(){
        BlazeJPAQuery<InvalidatedToken> jpaQuery = new BlazeJPAQuery<>(em,criteriaBuilderFactory);
        return jpaQuery.from(invalidatedToken).select(invalidatedToken).fetch();
    }
}
