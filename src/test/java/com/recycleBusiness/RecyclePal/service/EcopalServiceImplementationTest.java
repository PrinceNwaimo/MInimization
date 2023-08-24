package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.EcopalRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.request.UpdateEcopalRequest;
import com.recycleBusiness.RecyclePal.dto.response.EcopalRegistrationResponse;
import com.recycleBusiness.RecyclePal.dto.response.EcopalUpdateResponse;
import com.recycleBusiness.RecyclePal.exception.EcopalAlreadyExistException;
import com.recycleBusiness.RecyclePal.exception.EcopalNotSaveIntoDataBase;
import com.recycleBusiness.RecyclePal.exception.EcopalWithEmailOrUsernameExist;
import com.recycleBusiness.RecyclePal.exception.UsernameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EcopalServiceImplementationTest {
    @Autowired
    private EcopalServiceImplementation services;
    private EcopalRegistrationResponse registrationResponse;
   	private EcopalUpdateResponse updateResponse;
    private UpdateEcopalRequest updateEcopalRequest;


    @BeforeEach
    void setUp() throws EcopalAlreadyExistException {
        services.deleteAll();
        registrationResponse = new EcopalRegistrationResponse();
        EcopalRegistrationRequest registrationRequest = new EcopalRegistrationRequest();
        updateResponse = new EcopalUpdateResponse();
        updateEcopalRequest = new UpdateEcopalRequest();
        registrationRequest.setFirstName("Prince");
        registrationRequest.setLastName("Marv");
        registrationRequest.setHouseNumber("123");
        registrationRequest.setCity("Yaba");
        registrationRequest.setStreetName("Hebert");
        registrationRequest.setState("Lagos");
        registrationRequest.setEmail("idrisisah1@gmail.com");
        registrationRequest.setPassword("munirat_is_the_password");

      registrationResponse =  services.registration(registrationRequest);
    }
    @Test
    void testThatCustomerCanRegister(){
       assertThat(registrationResponse).isNotNull();
    }

    @Test
    void testThatCustomerCanUpdateHisProfile() throws EcopalWithEmailOrUsernameExist, UsernameNotFoundException {
        updateEcopalRequest.setFirstname("madina");
        updateEcopalRequest.setLastname("savage");

        updateResponse = services.updateProfile(updateEcopalRequest);
        assertThat(updateResponse.getFirstname().contains("iii")).isFalse();
        assertThat(updateResponse).isNotNull();
      assertThat(updateResponse.getFirstname().contains("madina") && updateResponse.getLastname().contains("savage")).isTrue();

    }
}