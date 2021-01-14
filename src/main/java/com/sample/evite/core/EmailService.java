package com.sample.evite.core;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import java.util.Properties;


@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${mail.from}")
    private String mailFrom;

    @Value("${spring.mail.properties.mail.smtp.port:}")
    private Integer port;

    @Value("${spring.mail.host:}")
    private String host;

    @Value("${spring.mail.properties.mail.transport.protocol:}")
    private String transportProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth:}")
    private Boolean isAuthorized;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable:}")
    private Boolean ttslEnabled;

    @Value("${spring.mail.properties.mail.smtp.starttls.required:}")
    private Boolean ttslRequired;

    @Value("${mail.enable.debug:}")
    private Boolean mailDebugEnabled;

    private final JavaMailSender mailSender;

    public void sendEMail(String recipientAddress, String subject, String body) {
        sendEMail(recipientAddress, subject, body, "HTML");
    }

    public void sendEMail(String recipientAddress, String subject, String body, String type) {

        //Configure mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        //configure mail settings
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.from", mailFrom);
        props.put("mail.transport.protocol", transportProtocol);
        props.put("mail.smtp.auth", isAuthorized);
        props.put("mail.smtp.starttls.enable", ttslEnabled);
        props.put("mail.smtp.starttls.required", ttslRequired);
        props.put("mail.debug", mailDebugEnabled);

        mailSender.setSession(Session.getInstance(props));

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));
            mimeMessage.setFrom(mailFrom);
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(body, type + "; charset=utf-8");
        };
        try {
            mailSender.send(preparator);
        } catch (MailException ex) {
           // Error log will be add here.
        }
    }
}
