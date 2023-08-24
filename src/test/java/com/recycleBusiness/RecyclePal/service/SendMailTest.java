package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.SendMailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendMailTest {


@BeforeEach
        public void SetUp() {
    SendMailRequest sendMailRequest = new SendMailRequest();
    sendMailRequest.setEmailSender("nwaimoprince12@gmail.com");
    sendMailRequest.setRecipients("victornedlloyd@gmail.com");
    sendMailRequest.setSubject("Welcome");
    sendMailRequest.setSetContent("You are welcome to recyclpal");
}
    @Test
    public  void testThatEmailIsSent(){


    }
}
