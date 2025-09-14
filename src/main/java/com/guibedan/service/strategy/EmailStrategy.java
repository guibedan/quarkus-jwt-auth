package com.guibedan.service.strategy;

import jakarta.mail.MessagingException;

public interface EmailStrategy {

    public void sendEmail(String to, String subject, String body) throws MessagingException;

}
