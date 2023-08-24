package com.recycleBusiness.RecyclePal.configuration;

import com.recycleBusiness.RecyclePal.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

import static com.recycleBusiness.RecyclePal.utils.AppUtils.JWT_SIGNING_SECRET;

@Configuration
public class BeanConfig {

    @Value(JWT_SIGNING_SECRET)
    private String jwt_secret;
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtUtils jwtUtils(){
        return new JwtUtils(jwt_secret);
    }

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private int mailPort;
    @Value("${spring.mail.protocol}")
    private String mailProtocol;
    @Value("${spring.mail.password}")
    private String mailPassword;
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("jwt.signing.secret")
    private String secret;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(mailPort);
        javaMailSender.setProtocol(mailProtocol);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.ssl.enable", "true");
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setPassword(mailPassword);
        javaMailSender.setUsername(mailUsername);
        return javaMailSender;
    }
}
