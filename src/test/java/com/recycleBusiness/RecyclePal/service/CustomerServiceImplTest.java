package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.CustomerRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.request.UpdateCustomerRequest;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerRegistrationResponse;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerUpdateResponse;
import com.recycleBusiness.RecyclePal.exception.CustomerNotSaveIntoDataBase;
import com.recycleBusiness.RecyclePal.exception.CustomerWithEmailOrUsernameExist;
import com.recycleBusiness.RecyclePal.exception.UsernameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

class CustomerServiceImplTest {
    @Autowired
    private  CustomerServiceImpl services;
    private CustomerRegistrationResponse registrationResponse;
   	private  CustomerUpdateResponse updateResponse;
    private UpdateCustomerRequest updateCustomerRequest;


    @BeforeEach
    void setUp() throws CustomerWithEmailOrUsernameExist, CustomerNotSaveIntoDataBase {
        services.deleteAll();
        registrationResponse = new CustomerRegistrationResponse();
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest();
        updateResponse = new CustomerUpdateResponse();
        updateCustomerRequest = new UpdateCustomerRequest();

        registrationRequest.setUsername("Idman");
        registrationRequest.setEmail("idrisisah1@gmail.com");
        registrationRequest.setPassword("munirat_is_the_password");
        registrationRequest.setAddress("idrishhhh");
      registrationResponse =  services.customerRegistration(registrationRequest);
    }
    @Test
    void testThatCustomerCanRegister(){
       assertThat(registrationResponse).isNotNull();
    }

    @Test
    void testThatCustomerCanUpdateHisProfile() throws CustomerWithEmailOrUsernameExist, UsernameNotFoundException {
        updateCustomerRequest.setFirstname("madina");
        updateCustomerRequest.setLastname("savage");

        updateResponse = services.updateProfile(updateCustomerRequest);
        assertThat(updateResponse.getFirstname().contains("iii")).isFalse();
        assertThat(updateResponse).isNotNull();
      assertThat(updateResponse.getFirstname().contains("madina") && updateResponse.getLastname().contains("savage")).isTrue();

    }
}