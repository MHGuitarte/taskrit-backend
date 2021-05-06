package com.mangh.taskrit.util.poji;


import javax.mail.MessagingException;

public interface EmailService {

    public void sendSimpleMessage(String to, String subject, String text) throws MessagingException;
}
