package com.recycleBusiness.RecyclePal.configuration;

import com.recycleBusiness.RecyclePal.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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


}
