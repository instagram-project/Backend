package com.gmail.insta.service;

import com.gmail.insta.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("emailService")
@PropertySource(value = "classpath:mail.properties")
public class EmailServiceImpl implements EmailService {

    private final Environment env;
    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(Environment env) {
        this.env = env;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    @Override
    public void sendRegistrationEmail(User user) {
        if (env.getProperty("mail.enabled").equals("true")) {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(user.getEmail());
            email.setSubject("Registration Confirmation");
            email.setText("You confirmation key: " + user.getConfirmationToken());
            email.setFrom(env.getProperty("mail.from"));
            mailSender.send(email);
        }
    }

    @Async
    @Override
    public void sendResetEmail(User user) {
        if (env.getProperty("mail.enabled").equals("true")) {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(user.getEmail());
            email.setSubject("Reset password");
            email.setText("You password reset key: " + user.getResetToken());
            email.setFrom(env.getProperty("mail.from"));
            mailSender.send(email);
        }
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(env.getProperty("mail.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("mail.port")));
        mailSender.setUsername(env.getProperty("mail.userName"));
        mailSender.setPassword(env.getProperty("mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
