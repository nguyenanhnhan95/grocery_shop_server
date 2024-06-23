package com.example.grocery_store_sales_online.config;

import com.example.grocery_store_sales_online.model.InvalidatedToken;
import com.example.grocery_store_sales_online.repository.token.InvalidatedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CheckDataSchedule {
    private  final InvalidatedTokenRepository invalidatedTokenRepository;
    private static final Logger log = LoggerFactory.getLogger(CheckDataSchedule.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "${cron.expression.checkDateInvalidatedToken}")
    public void checkDateInvalidatedToken() throws InterruptedException {
        invalidatedTokenRepository.findAllToken().forEach(token->{
            if(token.getExpiryTime().after(new Date())){
                invalidatedTokenRepository.delete(token);
            }
        });
    }
}
