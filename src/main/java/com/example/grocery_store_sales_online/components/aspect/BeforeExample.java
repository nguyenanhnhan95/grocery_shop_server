package com.example.grocery_store_sales_online.components.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class BeforeExample {
    @Before(value = "execution(* com.example.grocery_store_sales_online.controller.impl.AuthControllerImpl.login(..))")
    public void doAccessCheck(JoinPoint joinPoint){
        log.info(String.valueOf(joinPoint.getSignature()));
    }
}
