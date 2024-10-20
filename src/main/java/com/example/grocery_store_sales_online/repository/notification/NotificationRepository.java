package com.example.grocery_store_sales_online.repository.notification;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.example.grocery_store_sales_online.components.Notification;
import com.example.grocery_store_sales_online.model.person.QUser;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository extends BaseRepository<Notification,Long> {
    protected final QUser user = QUser.user;
    public NotificationRepository(EntityManager em, CriteriaBuilderFactory criteriaBuilderFactory) {
        super(Notification.class, em,criteriaBuilderFactory);
    }
}
