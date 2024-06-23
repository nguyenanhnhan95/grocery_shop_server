package com.example.grocery_store_sales_online.controller;

import com.example.grocery_store_sales_online.model.Notification;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.service.notification.NotificationService;
import com.example.grocery_store_sales_online.service.product.ProductService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
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
    private final ProductService productService;
    private final NotificationService notificationService;
    @GetMapping("/products")
    public ResponseEntity<QueryListResult<Product>> findProduct(@RequestParam("query") String queryParameter){
        return new ResponseEntity<>(productService.finAll(queryParameter), HttpStatus.OK);
    }
    @MessageMapping("/message")
    @SendTo("/topic/response")
    public ResponseEntity<List<Notification>> handle(String message) {
        return new ResponseEntity<>(notificationService.findAll(),HttpStatus.OK);
    }
}
