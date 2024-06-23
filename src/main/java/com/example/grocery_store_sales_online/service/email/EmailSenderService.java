package com.example.grocery_store_sales_online.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;
//    @Value("${spring.mail.username}")
//    private String username;
    public void sendEmail(String toEmail,String subject,String body) throws AddressException, MessagingException, IOException {
        SimpleMailMessage message = new SimpleMailMessage();
        StringBuilder result = new StringBuilder();
        result.append("[");
        result.append(subject);
        result.append("] Mail xác nhận đăng ký thàng viên T&N");

        message.setSubject(result.toString());
        result.setLength(0);
//        message.setFrom(username);
        message.setTo(toEmail);
        result.append("<h2>");
        result.append(body);
        result.append("</h2>");
        message.setText(result.toString());
        javaMailSender.send(message);
        System.out.println("Mail sent successfuly ....");
    }
}
