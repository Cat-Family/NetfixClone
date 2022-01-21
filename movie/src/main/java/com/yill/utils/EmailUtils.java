package com.yill.utils;

import com.yill.constant.EmaliConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

    @Value("${spring.mail.username}")
    private  String from;

    @Autowired
    private  JavaMailSender mailSender;


    public  Boolean sendEmail(String email,String title,String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom(from);
            message.setTo(email);
            message.setSubject(title);
            message.setText(text);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
