package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.components.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/home")
public interface IHomeController {
    @MessageMapping("/message")
    @SendTo("/topic/response")
    ResponseEntity<List<Notification>> handle(String message);
}
