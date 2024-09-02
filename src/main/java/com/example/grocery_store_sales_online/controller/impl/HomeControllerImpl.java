package com.example.grocery_store_sales_online.controller.impl;

import com.example.grocery_store_sales_online.components.Notification;
import com.example.grocery_store_sales_online.controller.IHomeController;
import com.example.grocery_store_sales_online.service.impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeControllerImpl implements IHomeController {
    private final NotificationServiceImpl notificationService;
    @Override
    public ResponseEntity<List<Notification>> handle(String message) {
        return new ResponseEntity<>(notificationService.findAll(),HttpStatus.OK);
    }
}
