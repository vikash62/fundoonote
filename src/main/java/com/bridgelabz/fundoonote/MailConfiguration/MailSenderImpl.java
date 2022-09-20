package com.bridgelabz.fundoonote.MailConfiguration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/*
Implementation of IMailSender to send mails
 */
@Service
public class MailSenderImpl implements IMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    /*
    method to send mail when rest api is called
     */
    @Override
    public void sendEmail(String to, String msg) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setText(msg);
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("pathakvikash826@gmail.com");
            messageHelper.setTo(to);
            messageHelper.setSubject("mail from admin");
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e+"hello");
            
        }

    }

 

}