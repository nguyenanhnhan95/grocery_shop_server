package com.example.grocery_store_sales_online.repository.notification;

import com.example.grocery_store_sales_online.model.Notification;
import com.example.grocery_store_sales_online.model.person.QUser;
import com.example.grocery_store_sales_online.repository.base.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository extends BaseRepository<Notification,Long> {
    protected final QUser user = QUser.user;
    public NotificationRepository( EntityManager em) {
        super(Notification.class, em);
    }
}
