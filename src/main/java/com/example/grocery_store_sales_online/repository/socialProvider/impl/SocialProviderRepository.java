package com.example.grocery_store_sales_online.repository.socialProvider.impl;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.model.person.QSocialProvider;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import com.example.grocery_store_sales_online.repository.socialProvider.ISocialProviderRepository;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class SocialProviderRepository extends BaseRepository<SocialProvider,Long> implements ISocialProviderRepository {
    protected QSocialProvider socialProvider = QSocialProvider.socialProvider;
    public SocialProviderRepository( EntityManager em) {
        super(SocialProvider.class, em);
    }


    @Override
    public Optional<SocialProvider> findByProviderAndIdProvider(AuthProvider authProvider, String idProvider) {
        JPAQuery<SocialProvider> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(socialProvider).from(socialProvider)
                .where(socialProvider.providerId.eq(idProvider).and(socialProvider.provider.eq(authProvider)))
                .fetchOne());
    }

    @Override
    public List<SocialProvider> findByIdEmployee(Long idEmployee) {
        JPAQuery<SocialProvider> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(socialProvider).from(socialProvider)
                .where(socialProvider.employee.id.eq(idEmployee)).fetch();
    }

    @Override
    public List<SocialProvider> findByIdUser(Long idUser) {
        JPAQuery<SocialProvider> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(socialProvider).from(socialProvider)
                .where(socialProvider.user.id.eq(idUser)).fetch();
    }

    @Override
    public Optional<SocialProvider> findByProviderId(String providerId) {
        JPAQuery<SocialProvider> jpaQuery = new JPAQuery<>(em);
        return Optional.ofNullable(jpaQuery.select(socialProvider).from(socialProvider)
                .where(socialProvider.providerId.eq(providerId)).fetchFirst());
    }
}
