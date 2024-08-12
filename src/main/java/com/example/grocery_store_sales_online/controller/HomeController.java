package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.components.Notification;
import com.example.grocery_store_sales_online.service.notification.impl.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final NotificationService notificationService;
    @MessageMapping("/message")
    @SendTo("/topic/response")
    public ResponseEntity<List<Notification>> handle(String message) {
        return new ResponseEntity<>(notificationService.findAll(),HttpStatus.OK);
    }
}
