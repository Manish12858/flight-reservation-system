package com.manish.flightreservation.util;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EmailUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(EmailUtil.class);
    @Value("${com.manish.flightreservation.itinerary.email.body}")
    public String EMAIL_BODY;
    @Value("${com.manish.flightreservation.itinerary.email.subject}")
    public String EMAIL_SUBJECT;


    @Autowired
    private JavaMailSender sender;

    public void sendItineraryMail(String toAddress, String filePath) {
        LOGGER.info("Inside sendItineraryMail()");
        MimeMessage message = sender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(toAddress);
            messageHelper.setSubject(EMAIL_SUBJECT);
            messageHelper.setText(EMAIL_BODY);
            messageHelper.addAttachment("itinerary ", new File(filePath));
            sender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Exception inside sendItinerary "+e.getMessage());

        }

    }


}
