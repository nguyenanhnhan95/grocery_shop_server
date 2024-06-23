package com.example.grocery_store_sales_online.config;
import com.example.grocery_store_sales_online.model.Notification;
import com.example.grocery_store_sales_online.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Configuration
@EnableScheduling
public class NotificationConfig {
    @Autowired
    private NotificationService notificationService;
    private static final Logger log = LoggerFactory.getLogger(NotificationConfig.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "${cron.expression.notification}")
    public void reportCurrentTime() throws InterruptedException {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//        List<Notification> list=notificationService.findAll();
//        Notification notification = new Notification();
//        notification.setText(list.size()+1+"Số thông báo");
//        notificationService.saveNotification(notification);
    }
}
