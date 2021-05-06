package com.mangh.taskrit.util.pojo;

import com.mangh.taskrit.util.poji.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendSimpleMessage(final String to, final String subject, final String body) throws MessagingException {
        final MimeMessage message = this.getJavaMailSender().createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setFrom("soporte.tiendainterna@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        this.getJavaMailSender().send(message);
    }

    private JavaMailSender getJavaMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("soporte.tiendainterna@gmail.com");
        mailSender.setPassword("FWb'9%X0");

        final Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
