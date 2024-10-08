package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.components.Notification;
import com.example.grocery_store_sales_online.repository.notification.NotificationRepository;
import com.example.grocery_store_sales_online.service.INotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl extends BaseServiceImpl implements INotificationService<Notification> {
    private  final NotificationRepository notificationRepository;
    @Override
    public void saveNotification(Notification notification) {
        try {
            log.info("NotificationService:saveNotification execution started.");
            setMetaData(notification);
            setPersonAction(notification);
            notificationRepository.save(notification);
        }catch (Exception ex){
            log.error("Exception occurred while persisting NotificationService:saveNotification to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<Notification> findAll() {
        try {
            log.info("NotificationService:findAll execution started.");
            return notificationRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting NNotificationService:findAll to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
}
