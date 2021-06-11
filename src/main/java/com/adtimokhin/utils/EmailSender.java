package com.adtimokhin.utils;

import com.adtimokhin.models.user.User;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author adtimokhin
 * 11.06.2021
 **/

@Component
public class EmailSender {

    private final String email = "adtimokhin@gmail.com";
    private final String emailPassword = "";
    private final String serverURL = "localhost:8080/";
    private final String VERIFY_URL = "localhost:8080/verify/";

    private static final Logger logger = Logger.getLogger("file");



    @Bean
    Properties getEmailProperties(){
        // todo: Store this properties in a property file
        Properties properties = new Properties();
        properties.put("mail.adtimokhin.auth" , "true");
        properties.put("mail.adtimokhin.starttls.enable" , "true");
        properties.put("mail.adtimokhin.host" , "adtimokhin@gmail.com");
        properties.put("mail.adtimokhin.port" , "587");

        return properties;
    }


    //Todo: test it and see that it works.
    public void sentEmailVerificationLetter(User user){
        Properties properties = new Properties();
        properties.put("mail.adtimokhin.auth" , "true");
        properties.put("mail.adtimokhin.starttls.enable" , "true");
        properties.put("mail.adtimokhin.host" , "adtimokhin@gmail.com");
        properties.put("mail.adtimokhin.port" , "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email , emailPassword);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO , new InternetAddress(user.getEmail()));
            message.setSubject("Verification email.");
            message.setText("Follow the link below to verify your email.:\n " + VERIFY_URL + user.getEmailVerificationToken());
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

}
