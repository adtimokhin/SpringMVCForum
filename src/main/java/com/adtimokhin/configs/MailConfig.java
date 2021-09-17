package com.adtimokhin.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author adtimokhin
 * 29.07.2021
 **/
@Configuration
@PropertySource("classpath:email.properties")
public class MailConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.debug}")
    private String debug;

//    @Value("${mail.stmp.auth}")
//    private String auth;
//
//    @Value("${mail.stmp.ssl.enable}")
//    private String enableSSL;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol" , protocol);
        properties.setProperty("mail.debug" , debug);


        return javaMailSender;
    }
}
