package com.adtimokhin.utils.email;

import com.adtimokhin.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 11.06.2021
 **/

@Component
public class SimpleEmailSender {


    @Autowired
    private JavaMailSender javaMailSender;

    private static final String HOST_URL = "localhost:8080/";

    @Value("${mail.username}")
    private String email;

    public void sentSimpleEmail() {
    }

    public void sentEmailVerificationLetter(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(email);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Email verification for the antibullying website.");

        mailMessage.setText("Thank you for registering on our website. To be able to use your account you have to verify your email. Press the link below to do so. \n\n\n\n " + HOST_URL + "verify/" + user.getEmailVerificationToken());

        javaMailSender.send(mailMessage);
    }

    public void sentPasswordRestoreEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(email);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Restore password.");

        mailMessage.setText("Follow this link: \n\n\n" + HOST_URL + "restore_password/" + user.getPasswordRestoreToken());
        javaMailSender.send(mailMessage);
    }

}
